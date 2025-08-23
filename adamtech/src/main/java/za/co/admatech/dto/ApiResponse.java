package za.co.admatech.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, Object> metadata;
    private LocalDateTime timestamp;

    // Constructors
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
        this.metadata = new HashMap<>();
    }

    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    public ApiResponse(boolean success, String message, T data, Map<String, Object> metadata) {
        this(success, message, data);
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }

    // Static factory methods for success responses
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message, T data, Map<String, Object> metadata) {
        return new ApiResponse<>(true, message, data, metadata);
    }

    // Static factory methods for error responses
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    // Pagination helper
    public static Map<String, Object> createPaginationMetadata(int page, int size, long totalElements, int totalPages) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("page", page);
        metadata.put("size", size);
        metadata.put("totalElements", totalElements);
        metadata.put("totalPages", totalPages);
        return metadata;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Utility method to add metadata
    public ApiResponse<T> addMetadata(String key, Object value) {
        this.metadata.put(key, value);
        return this;
    }
}
