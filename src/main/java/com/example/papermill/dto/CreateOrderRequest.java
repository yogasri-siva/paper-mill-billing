package com.example.papermill.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents the JSON payload sent by the client when placing a new order.
 * Validation annotations ensure required fields are present and positive
 * where appropriate.  Note that width can be fractional; integer values are
 * converted automatically by Spring MVC.
 */
public class CreateOrderRequest {

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private Double width;

    @NotNull
    private Integer gsm;

    @NotNull
    private Integer bf;

    @NotNull
    private Integer cobb;

    // Getters and setters
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
}