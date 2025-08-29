package za.co.admatech.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService implements IFileUploadService {

    @Value("${app.upload.dir:uploads/images/}")
    private String uploadDir;

    @Value("${app.upload.allowed-types:image/jpeg,image/jpg,image/png,image/gif,image/webp}")
    private String[] allowedTypes;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot upload empty file");
        }

        if (!isValidImageType(file)) {
            throw new IOException("Invalid file type. Only image files are allowed.");
        }

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String fileName = timestamp + "_" + uniqueId + "." + fileExtension;

        // Save the file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public List<String> uploadMultipleImages(List<MultipartFile> files) throws IOException {
        List<String> uploadedFiles = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = uploadImage(file);
                uploadedFiles.add(fileName);
            }
        }
        
        return uploadedFiles;
    }

    @Override
    public boolean deleteImage(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean isValidImageType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && Arrays.asList(allowedTypes).contains(contentType.toLowerCase());
    }

    @Override
    public String getImageUrl(String fileName) {
        return "/adamtech/api/files/images/" + fileName;
    }

    @Override
    public byte[] getImage(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        return Files.readAllBytes(filePath);
    }
}
