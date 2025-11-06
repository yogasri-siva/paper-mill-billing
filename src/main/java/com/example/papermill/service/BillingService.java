package com.example.papermill.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Business logic for computing the price of a paper order.  The pricing
 * algorithm is based on the sample C function provided by the user.  GSM
 * (grams per square metre) determines a base price band, and BF (burst
 * factor) adds incremental surcharges.  After computing the base unit
 * price the service multiplies by the ordered quantity and adds 12 % GST.
 */
@Service
public class BillingService {

    /**
     * Encapsulates the detailed price calculation.  The pricePerUnit field
     * represents the raw unit cost before GST, subtotal is pricePerUnit
     * multiplied by quantity, gst is 12 % of the subtotal, and total is the
     * final amount payable.
     */
    public static class BillBreakdown {
        public final BigDecimal pricePerUnit;
        public final BigDecimal subtotal;
        public final BigDecimal gst;
        public final BigDecimal total;

        public BillBreakdown(BigDecimal pricePerUnit, BigDecimal subtotal,
                             BigDecimal gst, BigDecimal total) {
            this.pricePerUnit = pricePerUnit;
            this.subtotal = subtotal;
            this.gst = gst;
            this.total = total;
        }
    }

    /**
     * Compute the bill for a paper order.  The calculation follows the logic
     * of the provided C code, with some refinements: GSM determines the base
     * price band; BF increments the price by 500 for each 2‑point step above
     * 16; GST of 12 % is then applied to the subtotal.  Width and Cobb are
     * accepted as parameters for future extensibility but are not used in
     * pricing for now.
     *
     * @param quantity number of units ordered
     * @param width width of the paper (unused)
     * @param gsm grams per square metre
     * @param bf burst factor
     * @param cobb Cobb value (unused)
     * @return a breakdown of pricing details
     */
    public BillBreakdown computeBill(int quantity, double width, int gsm, int bf, int cobb) {
        // Determine base price from GSM band.  Start with default value.
        int price = 39500;
        if (gsm >= 120 && gsm < 140) {
            price = 40000;
        } else if (gsm >= 181 && gsm < 201) {
            price = 39700;
        } else if (gsm >= 210 && gsm < 221) {
            price = 40000;
        } else if (gsm >= 221 && gsm < 251) {
            price = 40500;
        } else if (gsm >= 251 && gsm < 2) {
            // This condition is intentionally left to mirror the original C
            // function even though it will never match.  It serves as a
            // placeholder for future ranges.
            price = 41500;
        }

        // Compute surcharges based on BF.  The original C code attempted to
        // add 500 for each 2‑point BF band starting at 16 (i.e., 16, 18, 20,
        // 22, 24) but contained a logic bug that caused the surcharge to be
        // added irrespective of the BF value.  Here we implement the intended
        // behaviour: for every two points above or equal to 16, add 500.
        int increments = 0;
        for (int i = 16; i <= 24; i += 2) {
            if (bf >= i) {
                increments++;
            }
        }
        int finalPricePerUnitInt = price + (increments * 500);

        // Convert to BigDecimal with two decimal places.  Pricing is in
        // currency units such as INR.
        BigDecimal pricePerUnit = BigDecimal.valueOf(finalPricePerUnitInt)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotal = pricePerUnit.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal gst = subtotal.multiply(new BigDecimal("0.12"))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(gst).setScale(2, RoundingMode.HALF_UP);

        return new BillBreakdown(pricePerUnit, subtotal, gst, total);
    }
}