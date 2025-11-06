package com.example.papermill.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * JPA entity representing a customer’s order for paper.  Each order stores
 * the paper parameters used for pricing as well as the final computed price
 * including GST.  The timestamp is captured when the order is created.
 */
@Entity
public class PaperOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Quantity ordered.  The unit (tons, sheets, rolls) is not fixed in the
     * business logic but can be interpreted consistently by the client. */
    private Integer quantity;

    /** Width of the paper stock.  The C sample accepted this value but did
     * nothing with it; future enhancements could adjust pricing based on
     * width. */
    private Double width;

    private Integer gsm;
    private Integer bf;
    private Integer cobb;

    /** Total price for this order including GST. */
    @Column(precision = 18, scale = 2)
    private BigDecimal price;

    /** Timestamp of order creation. */
    private Instant timestamp;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Integer getGsm() {
        return gsm;
    }

    public void setGsm(Integer gsm) {
        this.gsm = gsm;
    }

    public Integer getBf() {
        return bf;
    }

    public void setBf(Integer bf) {
        this.bf = bf;
    }

    public Integer getCobb() {
        return cobb;
    }

    public void setCobb(Integer cobb) {
        this.cobb = cobb;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}