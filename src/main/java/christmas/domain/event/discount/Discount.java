package christmas.domain.event.discount;

import christmas.domain.order.Order;

public interface Discount {
    int calculateDiscount(Order order);
}
