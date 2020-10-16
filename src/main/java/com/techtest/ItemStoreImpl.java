package com.techtest;

import java.util.HashMap;
import java.util.Map;

public class ItemStoreImpl implements ItemStore {

    Map<String, Item> itemMap;

    public ItemStoreImpl() {
        itemMap = new HashMap<String, Item>();
        populateItemStore();
    }

    /**
     * Added basic items to itemStore directly as would expect this to be in a DB for ease of updating in a real world scenario
     */
    private void populateItemStore() {
        add(new Item("001", "Travel Card Holder", 9.25));
        add(new Item("002", "Personalised cufflinks", 45.00));
        add(new Item("003", "Kids T-Shirt", 19.95));
    }

    @Override
    public void add(Item item) {
        itemMap.put(item.getProductCode(), item);
    }

    @Override
    public Boolean exists(String productCode) {
        return itemMap.get(productCode) != null;
    }

    @Override
    public String getName(String productCode) {
        if (itemMap.get(productCode) == null) {
            throw new IllegalArgumentException();
        }
        return itemMap.get(productCode).getName();
    }

    @Override
    public double getPrice(String productCode) {
        if (itemMap.get(productCode) == null) {
            throw new IllegalArgumentException();
        }
        return itemMap.get(productCode).getPrice();
    }
}
