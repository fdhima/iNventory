package com.tutorial.inventory.stock;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tutorial.inventory.product.Product;
import com.tutorial.inventory.shared.enums.Location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "stock_levels",
    uniqueConstraints = @UniqueConstraint(columnNames = {"product_id"})
)
public class StockLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int threshold;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public StockLevel() {}

    public StockLevel(Product product, Location location, int level, int threshold) {
        this.product = product;
        this.location = location;
        this.level = level;
        this.threshold = threshold;
    }

    public UUID getId() { return id; }
    public Product getProduct() { return product; }
    public Location getLocation() { return location; }
    public int getLevel() { return level; }
    public int getThreshold() { return threshold; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setProduct(Product product) { this.product = product; }
    public void setLocation(Location location) { this.location = location; }
    public void setLevel(int level) { this.level = level; }
    public void setThreshold(int threshold) { this.threshold = threshold; }

}
