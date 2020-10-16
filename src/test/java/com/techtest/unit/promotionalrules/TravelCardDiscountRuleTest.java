package com.techtest.unit.promotionalrules;


import com.techtest.OrderImpl;
import com.techtest.promotionalrules.PromotionalRule;
import com.techtest.promotionalrules.TravelCardDiscountRule;
import org.junit.Before;
import org.junit.Test;


import static org.mockito.Mockito.*;

public class TravelCardDiscountRuleTest {

    private OrderImpl order;
    private static final String PRODUCT_CODE_001 = "001";

    @Before
    public void setup() {
        order = mock(OrderImpl.class);
    }

    /**
     * Verifies the multi-buy discount is applied for Travel Card Holders when the minimum quantity threshold is met
     */
    @Test
    public void shouldApplyDiscount() {
        double discountPrice = 8.50;
        PromotionalRule promotionalRule = new TravelCardDiscountRule();
        when(order.getTotalQuantity(PRODUCT_CODE_001)).thenReturn(10L);

        promotionalRule.apply(order);

        verify(order).setDiscountedUnitPrice(PRODUCT_CODE_001, discountPrice);
    }

    /**
     * Verifies the multi-buy discount is applied for Travel Card Holders when the minimum quantity threshold is not met
     */
    @Test
    public void shouldNotApplyDiscount() {

        double discountPrice = 8.50;
        PromotionalRule promotionalRule = new TravelCardDiscountRule();
        when(order.getTotalQuantity(PRODUCT_CODE_001)).thenReturn(1L);

        promotionalRule.apply(order);

        verify(order, times(0)).setDiscountedUnitPrice(PRODUCT_CODE_001, discountPrice);
    }

}
