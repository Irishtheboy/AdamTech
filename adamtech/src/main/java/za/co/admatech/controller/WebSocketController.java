package za.co.admatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Handle cart updates
    @MessageMapping("/cart/update")
    @SendTo("/topic/cart/updates")
    public CartUpdateMessage handleCartUpdate(@RequestBody CartUpdateMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    // Handle order status updates
    @MessageMapping("/order/status")
    @SendTo("/topic/order/status")
    public OrderStatusMessage handleOrderStatus(@RequestBody OrderStatusMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    // Handle inventory updates
    @MessageMapping("/inventory/update")
    @SendTo("/topic/inventory/updates")
    public InventoryUpdateMessage handleInventoryUpdate(@RequestBody InventoryUpdateMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    // Send real-time notifications to specific users
    public void sendToUser(String username, String destination, Object message) {
        messagingTemplate.convertAndSendToUser(username, destination, message);
    }

    // Send broadcast messages
    public void sendToAll(String destination, Object message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    // Message classes
    public static class CartUpdateMessage {
        private String userId;
        private String action; // "add", "remove", "update"
        private String productId;
        private Integer quantity;
        private LocalDateTime timestamp;

        // Constructors, getters, setters
        public CartUpdateMessage() {}

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    public static class OrderStatusMessage {
        private String orderId;
        private String status;
        private String userId;
        private LocalDateTime timestamp;

        // Constructors, getters, setters
        public OrderStatusMessage() {}

        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    public static class InventoryUpdateMessage {
        private String productId;
        private Integer stockLevel;
        private String action; // "restock", "low_stock", "out_of_stock"
        private LocalDateTime timestamp;

        // Constructors, getters, setters
        public InventoryUpdateMessage() {}

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        
        public Integer getStockLevel() { return stockLevel; }
        public void setStockLevel(Integer stockLevel) { this.stockLevel = stockLevel; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}
