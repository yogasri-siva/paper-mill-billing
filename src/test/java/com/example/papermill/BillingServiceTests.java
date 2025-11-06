package com.example.papermill;

import com.example.papermill.service.BillingService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit tests for the BillingService.  These tests ensure that
 * calculating the bill produces sensible values.  Additional assertions
 * could be added to verify specific pricing scenarios.
 */
public class BillingServiceTests {

    @Test
    void testComputeBillReturnsNonZeroTotal() {
        BillingService service = new BillingService();
        BillingService.BillBreakdown breakdown = service.computeBill(10, 36.0, 120, 18, 30);
        assertNotNull(breakdown);
        assertTrue(breakdown.total.compareTo(BigDecimal.ZERO) > 0,
                "Total should be positive");
    }
}