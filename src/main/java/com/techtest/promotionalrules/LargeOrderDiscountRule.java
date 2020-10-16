package com.techtest.promotionalrules;

import com.techtest.OrderImpl;

public class LargeOrderDiscountRule implements PromotionalRule {
    @Override
    public OrderImpl apply(OrderImpl order) {
        double totalPrice = order.calculateTotal();

        /**
         * If totalPrice is equal to or greater than 60.00 then apply a 0.9 multiplier to apply a 10% Global Discount
         */
        if (totalPrice >= 60.0) {
            order.setGlobalDiscountStatus(true);
            order.setGlobalDiscountValue(0.9);
        }
        return order;
    }
}
