package christmas.domain.event.discount.strategy;

import christmas.domain.constants.event.EventConstants;
import christmas.domain.event.discount.Discount;
import christmas.domain.menu.MenuCategory;
import christmas.domain.order.Order;
import java.util.Set;

public class DayOfWeekDiscountStrategy implements Discount {
    private final MenuCategory menuCategory;
    private final int discountAmount;
    private final Set<Integer> applicableDayOfWeekIndices;

    public DayOfWeekDiscountStrategy(MenuCategory menuCategory, int discountAmount, Set<Integer> applicableDayOfWeekIndices) {
        this.menuCategory = menuCategory;
        this.discountAmount = discountAmount;
        this.applicableDayOfWeekIndices = applicableDayOfWeekIndices;
    }

    @Override
    public int calculateDiscount(Order order) {

        int dayOfWeekIndex = convertDayOfMonthToDayOfWeek(order.getVisitDay());

        if (!applicableDayOfWeekIndices.contains(dayOfWeekIndex)) {
            return 0;
        }

        return order.getItems().entrySet().stream()
                .filter(entry -> entry.getKey().getCategory() == this.menuCategory)
                .mapToInt(entry -> entry.getValue() * this.discountAmount)
                .sum();
    }

    public int convertDayOfMonthToDayOfWeek(int dayOfMonth) {
        final int firstDayIndex = EventConstants.DECEMBER_FIRST_DAY_OF_WEEK_INDEX.getValue();
        return (firstDayIndex + dayOfMonth - 1) % 7;
    }
}
