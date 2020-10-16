package com.techtest.promotionalrules;

import com.techtest.OrderImpl;

public interface PromotionalRule {

    OrderImpl apply(OrderImpl order);
}
