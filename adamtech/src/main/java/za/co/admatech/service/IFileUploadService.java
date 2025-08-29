package za.co.admatech.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface IFileUploadService {
    String uploadImage(MultipartFile file) throws IOException;
    List<String> uploadMultipleImages(List<MultipartFile> files) throws IOException;
    boolean deleteImage(String fileName);
    boolean isValidImageType(MultipartFile file);
    String getImageUrl(String fileName);
    byte[] getImage(String fileName) throws IOException;
}
