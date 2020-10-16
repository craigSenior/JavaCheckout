package com.techtest.acceptance;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.techtest.*;
import com.techtest.promotionalrules.LargeOrderDiscountRule;
import com.techtest.promotionalrules.PromotionalRule;
import com.techtest.promotionalrules.TravelCardDiscountRule;
import org.junit.Before;
import org.junit.Test;

public class AcceptanceTest {

    private static final String PRODUCT_CODE_001 = "001";
    private static final String PRODUCT_CODE_002 = "002";
    private static final String PRODUCT_CODE_003 = "003";

    private List<PromotionalRule> promotionalRules;
    private ItemStore itemStore;

    /**
     * Setup the itemStore and apply the promotional rules
     */
    @Before
    public void setup() {
        itemStore = new ItemStoreImpl();
        promotionalRules = new ArrayList<PromotionalRule>();
        promotionalRules.add(new TravelCardDiscountRule());
        promotionalRules.add(new LargeOrderDiscountRule());
    }

    /*  Basket: 001,002,003  */
    @Test
    public void scenarioOne() {
        Checkout checkout = new CheckoutImpl(itemStore, promotionalRules);
        checkout.scan(PRODUCT_CODE_001);
        checkout.scan(PRODUCT_CODE_002);
        checkout.scan(PRODUCT_CODE_003);

        double expected = 66.78;
        double actual = checkout.total();

        assertEquals(expected, actual, 0);
    }

    /* Basket: 001,003,001 */
    @Test
    public void scenarioTwo() {
        Checkout checkout = new CheckoutImpl(itemStore, promotionalRules);
        checkout.scan(PRODUCT_CODE_001);
        checkout.scan(PRODUCT_CODE_003);
        checkout.scan(PRODUCT_CODE_001);

        double expected = 36.95;
        double actual = checkout.total();

        assertEquals(expected, actual, 0);
    }

    /* Basket: 001,002,001,003 */
    @Test
    public void scenarioThree() {
        Checkout checkout = new CheckoutImpl(itemStore, promotionalRules);
        checkout.scan(PRODUCT_CODE_001);
        checkout.scan(PRODUCT_CODE_002);
        checkout.scan(PRODUCT_CODE_001);
        checkout.scan(PRODUCT_CODE_003);

        double expected = 73.76;
        double actual = checkout.total();

        assertEquals(expected, actual, 0);
    }
}
