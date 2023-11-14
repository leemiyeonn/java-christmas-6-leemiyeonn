package christmas.domain.order;

import christmas.domain.constants.event.EventType;
import christmas.domain.constants.event.reward.Badge;
import christmas.domain.constants.event.reward.Gift;
import christmas.domain.menu.MenuItem;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Order {
    private final int visitDay;
    private final Map<MenuItem, Integer> items;
    private final int orderTotal;
    private final Map<EventType, Integer> discountDetails = new EnumMap<>(EventType.class);
    private final Set<Gift> gifts = new LinkedHashSet<>();
    private final Set<Badge> badges = new LinkedHashSet<>();

    public Order(int visitDay, Map<MenuItem, Integer> items, int orderTotal) {
        this.visitDay = visitDay;
        this.items = items;
        this.orderTotal = orderTotal;
    }

    public int getVisitDay() {
        return visitDay;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public Map<EventType, Integer> getDiscountDetails() {
        return discountDetails;
    }

    public int getDiscountAmount() {
        return discountDetails.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Set<Gift> getGifts() {
        return gifts;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void applyDiscount(EventType eventType, int discount) {
        discountDetails.merge(eventType, discount, Integer::sum);
    }

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public int getExpectedPaymentAfterDiscount() {
        return orderTotal - getDiscountAmount();
    }

    public int calculateTotalBenefitAmount() {
        int giftsTotalPrice = gifts.stream()
                .mapToInt(Gift::getPrice)
                .sum();
        return getDiscountAmount() + giftsTotalPrice;
    }
}
