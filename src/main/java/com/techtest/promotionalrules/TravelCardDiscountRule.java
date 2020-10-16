package com.techtest.promotionalrules;

import com.techtest.OrderImpl;

public class TravelCardDiscountRule implements PromotionalRule {

    private final String productCode;
    private final int discountRequiredQuantity;
    private final double discountedPrice;

    public TravelCardDiscountRule() {
        this.productCode = "001";
        this.discountRequiredQuantity = 2;
        this.discountedPrice = 8.50;
    }

    @Override
    public OrderImpl apply(OrderImpl order) {
        Long purchaseQuantity = order.getTotalQuantity(productCode);

        /**
         * If quantity of the provided product code (001) is greater than the minimum threshold (2) then apply the discountedPrice
         */
        if (purchaseQuantity >= discountRequiredQuantity) {
            order.setDiscountedUnitPrice(productCode, discountedPrice);
        }
        return order;
    }
}
