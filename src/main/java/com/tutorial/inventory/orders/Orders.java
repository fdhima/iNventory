package com.tutorial.inventory.orders;

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

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Location location;

    @Column(name = "expected_arrival_at")
    private LocalDateTime expectedArrivalAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Orders() {}

    public Orders(Product product, int quantity, Location location, LocalDateTime expectedArrivalAt) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
        this.expectedArrivalAt = expectedArrivalAt;
    }

    public UUID getId() { return id; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }
    public LocalDateTime getExpectedArrivalAt() { return expectedArrivalAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setLocation(Location location) { this.location = location; }
    public void setExpectedArrivalAt(LocalDateTime expectedArrivalAt) { this.expectedArrivalAt = expectedArrivalAt; }

}
