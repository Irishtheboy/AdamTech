package za.co.admatech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.admatech.service.IFileUploadService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFileUploadService fileUploadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUploadImage_WithValidFile_ShouldReturnSuccess() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        when(fileUploadService.uploadImage(any())).thenReturn("uploaded-file.jpg");
        when(fileUploadService.getImageUrl("uploaded-file.jpg")).thenReturn("/adamtech/api/files/images/uploaded-file.jpg");

        // Act & Assert
        mockMvc.perform(multipart("/api/files/upload/image")
                .file(file)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Image uploaded successfully"))
                .andExpect(jsonPath("$.data.fileName").value("uploaded-file.jpg"))
                .andExpect(jsonPath("$.data.imageUrl").value("/adamtech/api/files/images/uploaded-file.jpg"));

        verify(fileUploadService).uploadImage(any());
        verify(fileUploadService).getImageUrl("uploaded-file.jpg");
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUploadImage_WithUserRole_ShouldReturnForbidden() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/api/files/upload/image")
                .file(file)
                .with(csrf()))
                .andExpect(status().isForbidden());

        verify(fileUploadService, never()).uploadImage(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUploadImage_WithServiceException_ShouldReturnBadRequest() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        when(fileUploadService.uploadImage(any())).thenThrow(new IOException("Upload failed"));

        // Act & Assert
        mockMvc.perform(multipart("/api/files/upload/image")
                .file(file)
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Failed to upload image: Upload failed"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUploadMultipleImages_WithValidFiles_ShouldReturnSuccess() throws Exception {
        // Arrange
        MockMultipartFile file1 = new MockMultipartFile("files", "test1.jpg", "image/jpeg", "test data 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "test2.png", "image/png", "test data 2".getBytes());
        
        List<String> fileNames = Arrays.asList("uploaded-file1.jpg", "uploaded-file2.png");
        when(fileUploadService.uploadMultipleImages(any())).thenReturn(fileNames);
        when(fileUploadService.getImageUrl("uploaded-file1.jpg")).thenReturn("/adamtech/api/files/images/uploaded-file1.jpg");
        when(fileUploadService.getImageUrl("uploaded-file2.png")).thenReturn("/adamtech/api/files/images/uploaded-file2.png");

        // Act & Assert
        mockMvc.perform(multipart("/api/files/upload/images")
                .file(file1)
                .file(file2)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Images uploaded successfully"))
                .andExpect(jsonPath("$.data.count").value(2));

        verify(fileUploadService).uploadMultipleImages(any());
    }

    @Test
    void testGetImage_WithExistingFile_ShouldReturnImage() throws Exception {
        // Arrange
        byte[] imageData = "test image data".getBytes();
        when(fileUploadService.getImage("test-image.jpg")).thenReturn(imageData);

        // Act & Assert
        mockMvc.perform(get("/api/files/images/test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(imageData));

        verify(fileUploadService).getImage("test-image.jpg");
    }

    @Test
    void testGetImage_WithNonExistentFile_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(fileUploadService.getImage("non-existent.jpg")).thenThrow(new IOException("File not found"));

        // Act & Assert
        mockMvc.perform(get("/api/files/images/non-existent.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteImage_WithExistingFile_ShouldReturnSuccess() throws Exception {
        // Arrange
        when(fileUploadService.deleteImage("test-image.jpg")).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/files/images/test-image.jpg")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Image deleted successfully"))
                .andExpect(jsonPath("$.data").value("test-image.jpg"));

        verify(fileUploadService).deleteImage("test-image.jpg");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteImage_WithNonExistentFile_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(fileUploadService.deleteImage("non-existent.jpg")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/files/images/non-existent.jpg")
                .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Image not found or could not be deleted"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testValidateFile_WithValidFile_ShouldReturnValidation() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        when(fileUploadService.isValidImageType(any())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(multipart("/api/files/validate")
                .file(file)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("File validation completed"))
                .andExpect(jsonPath("$.data.isValid").value(true))
                .andExpect(jsonPath("$.data.fileName").value("test.jpg"))
                .andExpect(jsonPath("$.data.contentType").value("image/jpeg"));

        verify(fileUploadService).isValidImageType(any());
    }
}
