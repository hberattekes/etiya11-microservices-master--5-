package com.etiya.orderservice.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Domain entity. Persistence is in-memory for now; later this can be mapped to a DB table.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int customerId;
    @Column(nullable = false)
    private int productId;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private String address;

    public Order() {
    }

    public Order(int id, int customerId, int productId, int quantity,
                 BigDecimal unitPrice, BigDecimal totalPrice, String address) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
