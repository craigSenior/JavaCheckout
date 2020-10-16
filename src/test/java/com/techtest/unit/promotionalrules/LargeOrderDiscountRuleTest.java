package com.techtest.unit.promotionalrules;

import com.techtest.OrderImpl;
import com.techtest.promotionalrules.LargeOrderDiscountRule;
import com.techtest.promotionalrules.PromotionalRule;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class LargeOrderDiscountRuleTest {

    private OrderImpl order;

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
    }

    /**
     * Verifies the discount is applied when the minimum spend threshold is met
     */
    @Test
    public void shouldApplyDiscount() {
        PromotionalRule promotionalRule = new LargeOrderDiscountRule();
        when(order.calculateTotal()).thenReturn(60.00);

        promotionalRule.apply(order);

        verify(order).setGlobalDiscountStatus(true);
        verify(order).setGlobalDiscountValue(0.9);
    }

    /**
     * Verifies the discount is not applied when the minimum spend threshold is not met
     */
    @Test
    public void shouldNotApplyDiscount() {
        PromotionalRule promotionalRule = new LargeOrderDiscountRule();
        when(order.calculateTotal()).thenReturn(59.99);

        promotionalRule.apply(order);

        verify(order, times(0)).setGlobalDiscountStatus(true);
        verify(order, times(0)).setGlobalDiscountValue(0.9);
    }

}
