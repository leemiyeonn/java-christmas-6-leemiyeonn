package christmas.domain.event;

import christmas.domain.constants.event.EventType;
import christmas.domain.event.discount.Discount;
import christmas.domain.event.reward.Reward;
import christmas.domain.order.Order;

public class Event {
    private final EventType eventType;
    private final EventPeriod period;
    private final Discount discountStrategy;
    private final Reward rewardStrategy;

    public Event(EventType eventType, EventPeriod period, Discount discountStrategy, Reward rewardStrategy) {
        this.eventType = eventType;
        this.period = period;
        this.discountStrategy = discountStrategy;
        this.rewardStrategy = rewardStrategy;
    }

    public EventType getEventType() {
        return eventType;
    }

    public boolean isDiscountType(EventType eventType) {
        return eventType == EventType.CHRISTMAS_DISCOUNT || eventType == EventType.WEEKDAY_DISCOUNT || eventType == EventType.WEEKEND_DISCOUNT || eventType == EventType.SPECIAL_DISCOUNT;
    }

    public boolean isApplicable(int dayOfMonth) {
        return period.isWithinPeriod(dayOfMonth);
    }

    public int calculateDiscount(Order order) {
        int dayOfMonth = order.getVisitDay();
        if (discountStrategy != null && isApplicable(dayOfMonth)) {
            return discountStrategy.calculateDiscount(order);
        }
        return 0;
    }

    public Object evaluateReward(Order order, int amount) {
        int dayOfMonth = order.getVisitDay();
        if (rewardStrategy != null && isApplicable(dayOfMonth)) {
            return rewardStrategy.evaluateReward(amount);
        }
        return null;
    }
}
