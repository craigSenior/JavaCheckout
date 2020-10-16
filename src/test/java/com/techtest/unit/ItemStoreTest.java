package com.techtest.unit;

import com.techtest.Item;
import com.techtest.ItemStore;
import com.techtest.ItemStoreImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemStoreTest {

    private final static String PRODUCT_CODE_999 = "999";
    private final static String NAME_999 = "Example Name";
    private final static double PRICE_999 = 13.0;

    private ItemStore itemStore;
    private Item item;

    @Before
    public void setup() {
        itemStore = new ItemStoreImpl();
        item = new Item(PRODUCT_CODE_999, NAME_999, PRICE_999);
    }

    /**
     * Verify false returned when item does not exist
     */
    @Test
    public void shouldReturnFalseIfItemDoesNotExist() {
        Boolean actual = itemStore.exists(PRODUCT_CODE_999);

        assertFalse(actual);
    }

    /**
     * Verify items are successfully added to the itemStore
     */
    @Test
    public void shouldAddItem() {
        itemStore.add(item);

        Boolean actual = itemStore.exists(PRODUCT_CODE_999);

        assertTrue(actual);
    }

    /**
     * Verify name of an item within the itemStore can be retrieved
     */
    @Test
    public void shouldGetName() {
        itemStore.add(item);
        String name = itemStore.getName(PRODUCT_CODE_999);

        boolean nameEqual = NAME_999.equals(name);

        assertTrue(nameEqual);
    }

    /**
     * Verify price of an item within the itemStore can be retrieved
     */
    @Test
    public void shouldGetPrice() {
        itemStore.add(item);
        double price = itemStore.getPrice(PRODUCT_CODE_999);

        boolean priceEqual = PRICE_999 == price;

        assertTrue(priceEqual);
    }

}
