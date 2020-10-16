package com.techtest;


public interface Order {

    Long getTotalQuantity(String productCode);

    double getFinalUnitPrice(String productCode);

    void setDiscountedUnitPrice(String productCode, double discountedPrice);

    double calculateTotal();
}
