package com.techtest.unit;

import com.techtest.ItemStore;
import com.techtest.OrderImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {

    private static final String PRODUCT_CODE_999 = "999";
    private static final String PRODUCT_CODE_777 = "777";

    private ItemStore itemStore;
    private OrderImpl order;

    @Before
    public void setup() {
        itemStore = mock(ItemStore.class);
    }

    /**
     * Verify when null param is provided IllegalArgumentException is thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNullParamPassedIn() {
        order = new OrderImpl(itemStore, null);
    }

    /**
     * Verify quantities set correctly.
     */
    @Test
    public void shouldSetQuantities() {
        order = new OrderImpl(itemStore, Arrays.asList(PRODUCT_CODE_999, PRODUCT_CODE_999, PRODUCT_CODE_999));
        Long expected = 3L;
        Long actual = order.getTotalQuantity(PRODUCT_CODE_999);

        assertEquals(expected, actual);
    }

    /**
     * Verify quantity is zero when no instances of the productCode are provided
     */
    @Test
    public void shouldReturnZeroQuantity() {
        order = new OrderImpl(itemStore, new ArrayList<String>());
        Long expected = 0L;
        Long actual = order.getTotalQuantity(PRODUCT_CODE_999);

        assertEquals(expected, actual);
    }

    /**
     * Verify discountedPrice can be set
     */
    @Test
    public void shouldSetDiscountedPrice() {
        order = new OrderImpl(itemStore, Arrays.asList(PRODUCT_CODE_999));
        double expected = 99.99;
        order.setDiscountedUnitPrice(PRODUCT_CODE_999, expected);
        double actual = order.getFinalUnitPrice(PRODUCT_CODE_999);

        assertEquals(expected, actual, 0);
    }

    /**
     * Verify calculation of total is zero when nothing is purchased
     */
    @Test
    public void shouldCalculateZeroTotalWhenNothingPurchased() {
        order = new OrderImpl(itemStore, Arrays.asList());
        double expected = 0.0;

        double actual = order.calculateTotal();

        assertEquals(expected, actual, 0);
    }

    /**
     * Verify calculation of total is correct for single item
     */
    @Test
    public void shouldCorrectlyCalculateTotalForOneItem() {
        double itemPrice = 13;
        when(itemStore.getPrice(anyString())).thenReturn(itemPrice);
        order = new OrderImpl(itemStore, Arrays.asList(PRODUCT_CODE_999));
        double expected = itemPrice;

        double actual = order.calculateTotal();

        assertEquals(expected, actual, 0);
    }

    /**
     * Verify calculation of total is correct for multiple items
     */
    @Test
    public void shouldCorrectlyCalculateTotalForMoreThanOneItem() {
        double itemPrice = 13;
        when(itemStore.getPrice(anyString())).thenReturn(itemPrice);
        order = new OrderImpl(itemStore, Arrays.asList(PRODUCT_CODE_999, PRODUCT_CODE_999, PRODUCT_CODE_777));
        double expected = itemPrice * 3;

        double actual = order.calculateTotal();

        assertEquals(expected, actual, 0);
    }

}
