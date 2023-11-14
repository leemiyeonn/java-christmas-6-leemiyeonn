package christmas.domain.event.discount.strategy;

import christmas.domain.event.discount.Discount;
import christmas.domain.order.Order;
import java.util.Set;

public class SpecialDiscountStrategy implements Discount {
    private final int discountAmount;
    private final Set<Integer> applicableDaysOfMonth;

    public SpecialDiscountStrategy(int discountAmount, Set<Integer> applicableDaysOfMonth) {
        this.discountAmount = discountAmount;
        this.applicableDaysOfMonth = applicableDaysOfMonth;
    }

    @Override
    public int calculateDiscount(Order order) {
        int dayOfMonth = order.getVisitDay();
        if (applicableDaysOfMonth.contains(dayOfMonth)) {
            return discountAmount;
        }
        return 0;
    }
}
