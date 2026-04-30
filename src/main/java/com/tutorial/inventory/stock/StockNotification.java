package com.tutorial.inventory.stock;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutorial.inventory.shared.enums.Location;

public class StockNotification {

    private UUID id;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_name")
    private String productName;

    private Location location;

    private int level;

    private int threshold;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public StockNotification() {}

    public UUID getId() { return id; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Location getLocation() { return location; }
    public int getLevel() { return level; }
    public int getThreshold() { return threshold; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(UUID id) { this.id = id; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setLocation(Location location) { this.location = location; }
    public void setLevel(int level) { this.level = level; }
    public void setThreshold(int threshold) { this.threshold = threshold; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}
