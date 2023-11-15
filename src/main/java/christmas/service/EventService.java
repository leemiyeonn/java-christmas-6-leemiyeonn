package christmas.service;

import christmas.domain.constants.event.EventType;
import christmas.domain.constants.event.reward.Badge;
import christmas.domain.constants.event.reward.Gift;
import christmas.domain.event.Event;
import christmas.domain.event.EventManager;
import christmas.domain.order.FinalizedOrder;
import christmas.domain.order.Order;
import christmas.domain.order.OrderDiscounts;
import christmas.domain.order.OrderRewards;
import java.util.Objects;

public class EventService {
    private final EventManager eventManager;

    public EventService() {
        this.eventManager = EventManager.getInstance();
    }

    public FinalizedOrder applyEventDiscountsAndRewards(Order order) {
        if (isOrderEligibleForEvents(order)) {
            return createFinalizedOrderWithEvents(order);
        }
        return createFinalizedOrderWithoutEvents(order);
    }

    private boolean isOrderEligibleForEvents(Order order) {
        return order.calculateOrderTotal() >= 10_000;
    }

    private FinalizedOrder createFinalizedOrderWithEvents(Order order) {
        OrderDiscounts discounts = calculateAllDiscounts(order);
        OrderRewards rewards = calculateAllRewards(order, discounts);
        return new FinalizedOrder(order, discounts, rewards);
    }

    private FinalizedOrder createFinalizedOrderWithoutEvents(Order order) {
        return new FinalizedOrder(order, new OrderDiscounts(), new OrderRewards());
    }

    private OrderDiscounts calculateAllDiscounts(Order order) {
        OrderDiscounts discounts = new OrderDiscounts();
        eventManager.getEvents().stream()
                .filter(event -> event.isDiscountType(event.getEventType()))
                .forEach(event -> applyEventDiscount(order, event, discounts));
        return discounts;
    }

    private void applyEventDiscount(Order order, Event event, OrderDiscounts discounts) {
        int discountAmount = event.calculateDiscount(order);
        discounts.applyDiscount(event.getEventType(), discountAmount);
    }

    private OrderRewards calculateAllRewards(Order order, OrderDiscounts discounts) {
        OrderRewards rewards = new OrderRewards();
        applyGiftReward(order, rewards);

        int totalBenefitAmount = calculateTotalBenefitAmount(discounts, rewards);
        applyBadgeReward(order, rewards, totalBenefitAmount);

        return rewards;
    }

    private void applyGiftReward(Order order, OrderRewards rewards) {
        eventManager.getEvents().stream()
                .filter(event -> event.getEventType() == EventType.GIFT_REWARD)
                .map(event -> event.evaluateReward(order, order.calculateOrderTotal()))
                .filter(Objects::nonNull)
                .map(reward -> (Gift) reward)
                .findFirst().ifPresent(rewards::addGift);
    }

    private void applyBadgeReward(Order order, OrderRewards rewards, int totalBenefitAmount) {
        eventManager.getEvents().stream()
                .filter(event -> event.getEventType() == EventType.BADGE_REWARD)
                .map(event -> event.evaluateReward(order, totalBenefitAmount))
                .filter(Objects::nonNull)
                .map(reward -> (Badge) reward)
                .findFirst().ifPresent(rewards::addBadge);

    }

    private int calculateTotalBenefitAmount(OrderDiscounts discounts, OrderRewards rewards) {
        return discounts.getDiscountAmount() + rewards.getTotalGiftsPrice();
    }
}
