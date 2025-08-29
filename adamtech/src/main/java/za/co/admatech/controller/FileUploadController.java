package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.admatech.dto.ApiResponse;
import za.co.admatech.service.IFileUploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileUploadController {

    private final IFileUploadService fileUploadService;

    @Autowired
    public FileUploadController(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImage(
            @RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileUploadService.uploadImage(file);
            String imageUrl = fileUploadService.getImageUrl(fileName);
            
            Map<String, String> result = new HashMap<>();
            result.put("fileName", fileName);
            result.put("imageUrl", imageUrl);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Image uploaded successfully", result));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Failed to upload image: " + e.getMessage(), null));
        }
    }

    @PostMapping("/upload/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> uploadMultipleImages(
            @RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> fileNames = fileUploadService.uploadMultipleImages(files);
            List<String> imageUrls = fileNames.stream()
                    .map(fileUploadService::getImageUrl)
                    .toList();
            
            Map<String, Object> result = new HashMap<>();
            result.put("fileNames", fileNames);
            result.put("imageUrls", imageUrls);
            result.put("count", fileNames.size());
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Images uploaded successfully", result));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Failed to upload images: " + e.getMessage(), null));
        }
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            byte[] imageBytes = fileUploadService.getImage(fileName);
            
            // Determine content type based on file extension
            String contentType = getContentType(fileName);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/images/{fileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteImage(@PathVariable String fileName) {
        boolean deleted = fileUploadService.deleteImage(fileName);
        
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Image deleted successfully", fileName));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Image not found or could not be deleted", null));
        }
    }

    @PostMapping("/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateFile(
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> validation = new HashMap<>();
        validation.put("isValid", fileUploadService.isValidImageType(file));
        validation.put("fileName", file.getOriginalFilename());
        validation.put("size", file.getSize());
        validation.put("contentType", file.getContentType());
        
        return ResponseEntity.ok(new ApiResponse<>(true, "File validation completed", validation));
    }

    private String getContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "png" -> MediaType.IMAGE_PNG_VALUE;
            case "gif" -> MediaType.IMAGE_GIF_VALUE;
            case "webp" -> "image/webp";
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
    }
}
