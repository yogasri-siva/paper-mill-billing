package com.example.papermill.dto;

import java.math.BigDecimal;

/**
 * Response returned to the client when an order is created.  Includes the
 * breakdown of pricing to aid transparency: price per unit (before GST),
 * subtotal, GST amount, and the grand total including GST.  The order ID is
 * also included so the user can refer back to the stored order.
 */
public class OrderResponse {
    private final Long orderId;
    private final BigDecimal pricePerUnit;
    private final BigDecimal subtotal;
    private final BigDecimal gst;
    private final BigDecimal total;

    public OrderResponse(Long orderId, BigDecimal pricePerUnit, BigDecimal subtotal,
                         BigDecimal gst, BigDecimal total) {
        this.orderId = orderId;
        this.pricePerUnit = pricePerUnit;
        this.subtotal = subtotal;
        this.gst = gst;
        this.total = total;
    }

    public Long getOrderId() {
        return orderId;
    }
    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public BigDecimal getGst() {
        return gst;
    }
    public BigDecimal getTotal() {
        return total;
    }
}