package com.techtest;

public class OrderRow {

    private long totalQuantity;
    private double originalUnitPrice;
    private double finalUnitPrice;

    public OrderRow(long totalQuantity, double unitPrice, double finalUnitPrice) {
        this.totalQuantity = totalQuantity;
        this.originalUnitPrice = unitPrice;
        this.finalUnitPrice = finalUnitPrice;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getOriginalUnitPrice() {
        return originalUnitPrice;
    }

    public void setOriginalUnitPrice(double originalUnitPrice) {
        this.originalUnitPrice = originalUnitPrice;
    }

    public double getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setFinalUnitPrice(double finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }

    @Override
    public String toString() {
        return "[totalQuantity=" + totalQuantity + ", originalUnitPrice=" + originalUnitPrice + ", finalUnitPrice=" + finalUnitPrice + "]";
    }

}
