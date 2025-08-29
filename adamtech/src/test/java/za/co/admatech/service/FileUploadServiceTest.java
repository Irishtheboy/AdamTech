package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileUploadServiceTest {

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private FileUploadService fileUploadService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(fileUploadService, "uploadDir", tempDir.toString() + "/");
        ReflectionTestUtils.setField(fileUploadService, "allowedTypes", 
            new String[]{"image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"});
    }

    @Test
    void testUploadImage_WithValidFile_ShouldReturnFileName() throws IOException {
        // Arrange
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");
        when(multipartFile.getContentType()).thenReturn("image/jpeg");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("test data".getBytes()));

        // Act
        String result = fileUploadService.uploadImage(multipartFile);

        // Assert
        assertNotNull(result);
        assertTrue(result.endsWith(".jpg"));
        assertTrue(Files.exists(tempDir.resolve(result)));
    }

    @Test
    void testUploadImage_WithEmptyFile_ShouldThrowException() {
        // Arrange
        when(multipartFile.isEmpty()).thenReturn(true);

        // Act & Assert
        IOException exception = assertThrows(IOException.class, 
            () -> fileUploadService.uploadImage(multipartFile));
        assertEquals("Cannot upload empty file", exception.getMessage());
    }

    @Test
    void testUploadImage_WithInvalidFileType_ShouldThrowException() {
        // Arrange
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getContentType()).thenReturn("text/plain");

        // Act & Assert
        IOException exception = assertThrows(IOException.class, 
            () -> fileUploadService.uploadImage(multipartFile));
        assertEquals("Invalid file type. Only image files are allowed.", exception.getMessage());
    }

    @Test
    void testUploadMultipleImages_WithValidFiles_ShouldReturnFileNames() throws IOException {
        // Arrange
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        
        when(file1.isEmpty()).thenReturn(false);
        when(file1.getOriginalFilename()).thenReturn("image1.png");
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getInputStream()).thenReturn(new ByteArrayInputStream("data1".getBytes()));
        
        when(file2.isEmpty()).thenReturn(false);
        when(file2.getOriginalFilename()).thenReturn("image2.jpg");
        when(file2.getContentType()).thenReturn("image/jpeg");
        when(file2.getInputStream()).thenReturn(new ByteArrayInputStream("data2".getBytes()));

        List<MultipartFile> files = Arrays.asList(file1, file2);

        // Act
        List<String> result = fileUploadService.uploadMultipleImages(files);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.get(0).endsWith(".png"));
        assertTrue(result.get(1).endsWith(".jpg"));
    }

    @Test
    void testDeleteImage_WithExistingFile_ShouldReturnTrue() throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("test-file.jpg");
        Files.createFile(testFile);
        assertTrue(Files.exists(testFile));

        // Act
        boolean result = fileUploadService.deleteImage("test-file.jpg");

        // Assert
        assertTrue(result);
        assertFalse(Files.exists(testFile));
    }

    @Test
    void testDeleteImage_WithNonExistentFile_ShouldReturnFalse() {
        // Act
        boolean result = fileUploadService.deleteImage("non-existent-file.jpg");

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidImageType_WithValidTypes_ShouldReturnTrue() {
        // Test cases for valid image types
        String[] validTypes = {"image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"};
        
        for (String type : validTypes) {
            when(multipartFile.getContentType()).thenReturn(type);
            assertTrue(fileUploadService.isValidImageType(multipartFile), 
                "Should accept " + type);
        }
    }

    @Test
    void testIsValidImageType_WithInvalidTypes_ShouldReturnFalse() {
        // Test cases for invalid types
        String[] invalidTypes = {"text/plain", "application/pdf", "video/mp4", null};
        
        for (String type : invalidTypes) {
            when(multipartFile.getContentType()).thenReturn(type);
            assertFalse(fileUploadService.isValidImageType(multipartFile), 
                "Should reject " + type);
        }
    }

    @Test
    void testGetImageUrl_ShouldReturnCorrectUrl() {
        // Act
        String result = fileUploadService.getImageUrl("test-image.jpg");

        // Assert
        assertEquals("/adamtech/api/files/images/test-image.jpg", result);
    }

    @Test
    void testGetImage_WithExistingFile_ShouldReturnBytes() throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("test-image.jpg");
        byte[] testData = "test image data".getBytes();
        Files.write(testFile, testData);

        // Act
        byte[] result = fileUploadService.getImage("test-image.jpg");

        // Assert
        assertArrayEquals(testData, result);
    }

    @Test
    void testGetImage_WithNonExistentFile_ShouldThrowException() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, 
            () -> fileUploadService.getImage("non-existent-file.jpg"));
        assertTrue(exception.getMessage().contains("File not found"));
    }
}
