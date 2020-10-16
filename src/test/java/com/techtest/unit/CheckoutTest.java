package com.techtest.unit;

import com.techtest.Checkout;
import com.techtest.CheckoutImpl;
import com.techtest.ItemStore;
import com.techtest.OrderImpl;
import com.techtest.promotionalrules.PromotionalRule;
import com.techtest.promotionalrules.TravelCardDiscountRule;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CheckoutTest {

    private final static String PRODUCT_CODE_999 = "999";

    private ItemStore itemStore;
    private List<PromotionalRule> promotionalRules;
    private Checkout checkout;

    @Before
    public void setup() {
        itemStore = mock(ItemStore.class);
        promotionalRules = new ArrayList<PromotionalRule>();
    }

    /**
     *  Verify scanning an unknown productCode throws an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenUnknownItemScanned() {
        checkout = new CheckoutImpl(itemStore, promotionalRules);
        checkout.scan("unknownProductCode");
    }

    /**
     *  Verify scanning an known productCode it is successfully added
     */
    @Test
    public void shouldScanItem() {
        when(itemStore.exists(anyString())).thenReturn(true);
        checkout = new CheckoutImpl(itemStore, promotionalRules);
        checkout.scan(PRODUCT_CODE_999);

        verify(itemStore).exists(PRODUCT_CODE_999);
    }

    /**
     *  Verify calling total returns the correct total for scanned items
     */
    @Test
    public void shouldTotalItems() {
        when(itemStore.exists(anyString())).thenReturn(true);
        when(itemStore.getPrice(anyString())).thenReturn(20.0);
        checkout = new CheckoutImpl(itemStore, promotionalRules);

        checkout.scan(PRODUCT_CODE_999);
        double expected = 20.0;
        double actual = checkout.total();

        assertEquals(expected, actual,0);
    }

    /**
     * Verify pricing rules are applied
     */
    @Test
    public void shouldApplyPricingRules() {
        PromotionalRule travelCardDiscountRule = mock(TravelCardDiscountRule.class);
        promotionalRules.add(travelCardDiscountRule);
        when(travelCardDiscountRule.apply(any())).thenReturn(new OrderImpl(itemStore, new ArrayList<String>()));
        when(itemStore.exists(anyString())).thenReturn(true);
        when(itemStore.getPrice(anyString())).thenReturn(20.0);
        checkout = new CheckoutImpl(itemStore, promotionalRules);

        checkout.scan(PRODUCT_CODE_999);

        double expected = 0.0;
        double actual = checkout.total();

        assertEquals(expected, actual, 0);
        verify(travelCardDiscountRule).apply(any());
    }
}
