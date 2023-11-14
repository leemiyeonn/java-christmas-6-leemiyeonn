package christmas.domain.event.discount.strategy;

import christmas.domain.event.discount.Discount;
import christmas.domain.menu.MenuCategory;
import christmas.domain.order.Order;

public class ChristmasDiscountStrategy implements Discount {
    private final int startDate;
    private final int discountStart;
    private final int discountIncrement;

    public ChristmasDiscountStrategy(int startDate, int discountStart, int discountIncrement) {
        this.startDate = startDate;
        this.discountStart = discountStart;
        this.discountIncrement = discountIncrement;
    }

    @Override
    public int calculateDiscount(Order order) {
        int dayOfMonth = order.getVisitDay();
        return discountStart + (dayOfMonth - startDate) * discountIncrement;
    }
}
