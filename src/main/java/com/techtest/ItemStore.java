package com.techtest;

import java.math.BigDecimal;

public interface ItemStore {

    void add(Item item);

    Boolean exists(String productCode);
    String getName(String productCode);
    double getPrice(String productCode);
}
