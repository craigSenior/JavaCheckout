package com.techtest;

import com.techtest.promotionalrules.PromotionalRule;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class CheckoutImpl implements Checkout {

    private final ItemStore itemStore;

    private final List<PromotionalRule> promotionalRules;
    private final List<String> productCodes;

    private static Logger logger = Logger.getLogger(CheckoutImpl.class.getName());

    /**
     * Added ItemStore dependency injection to faciliate easier unit testing through mocks
     * @param itemStore
     * @param promotionalRules
     */
    public CheckoutImpl(ItemStore itemStore, List<PromotionalRule> promotionalRules) {
        this.promotionalRules = promotionalRules;
        this.itemStore = itemStore;
        productCodes = new ArrayList<String>();
    }

    @Override
    public void scan(String productCode) {
        logger.log(Level.INFO,"Scanning item: {0}", productCode);

        if (itemStore.exists(productCode)) {
            productCodes.add(productCode);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public double total() {
        OrderImpl order = new OrderImpl(itemStore, productCodes);

        logger.log(Level.INFO,"Order before discounts: {0}", order);
        // Apply each pricing rule to the order
        for (PromotionalRule promotionalRule : promotionalRules) {
            order = promotionalRule.apply(order);
        }
        logger.log(Level.INFO,"Order after discounts: {0}", order);

        double total = order.calculateTotal();

        logger.log(Level.INFO,"Total order cost is: {0}", total);
        return total;
    }
}
