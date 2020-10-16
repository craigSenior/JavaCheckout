package com.techtest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderImpl implements Order {

    ItemStore itemStore;

    boolean globalDiscountStatus = false;
    double globalDiscountValue;

    public void setGlobalDiscountStatus(boolean globalDiscountStatus) {
        this.globalDiscountStatus = globalDiscountStatus;
    }

    public boolean getGlobalDiscountStatus() {
        return globalDiscountStatus;
    }

    public void setGlobalDiscountValue(double globalDiscountValue) {
        this.globalDiscountValue = globalDiscountValue;
    }

    public double getGlobalDiscountValue() {
        return globalDiscountValue;
    }

    /* An order is comprised of rows indexed by productCode */
    Map<String, OrderRow> order;

    public OrderImpl(ItemStore itemStore, List<String> productCodes) {
        this.itemStore = itemStore;

        if (null == productCodes || null == itemStore) {
            throw new IllegalArgumentException();
        }

        /* Group by product code and total quantity */
        Map<String, Long> quantityCount = productCodes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        /* Store order details */
        order = quantityCount.entrySet().stream().collect(HashMap<String, OrderRow>::new, (map, item) -> {
            String productCode = item.getKey();
            Long quantity = item.getValue();
            map.put(productCode, initRow(productCode, quantity));
        }, HashMap<String, OrderRow>::putAll);
    }

    private OrderRow initRow(String productCode, Long quantity) {
        return new OrderRow(quantity, itemStore.getPrice(productCode), itemStore.getPrice(productCode));
    }

    @Override
    public Long getTotalQuantity(String productCode) {
        return null == order.get(productCode) ? 0 : order.get(productCode).getTotalQuantity();
    }

    @Override
    public double getFinalUnitPrice(String productCode) {
        return null == order.get(productCode) ? 0.0 : order.get(productCode).getFinalUnitPrice();
    }

    @Override
    public void setDiscountedUnitPrice(String productCode, double discountedPrice) {
        if (null == order.get(productCode)) {
            throw new IllegalArgumentException();
        }
        order.get(productCode).setFinalUnitPrice(discountedPrice);
    }

    @Override
    public double calculateTotal() {
        double total = (order.values().stream()
                .mapToDouble(e -> e.getFinalUnitPrice()
                        * e.getTotalQuantity()).sum());
        if (globalDiscountStatus) {
            total = total * globalDiscountValue;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return this.order.toString();
    }
}
