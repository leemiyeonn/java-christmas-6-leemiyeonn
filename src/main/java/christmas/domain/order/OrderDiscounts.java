package christmas.domain.order;

import christmas.domain.constants.event.EventType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class OrderDiscounts {
    private final Map<EventType, Integer> discountDetails;

    public OrderDiscounts() {
        discountDetails = new EnumMap<>(EventType.class);
    }

    public Map<EventType, Integer> getDiscountDetails() {
        return Collections.unmodifiableMap(discountDetails);
    }

    public int getDiscountAmount() {
        return discountDetails.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void applyDiscount(EventType eventType, int discount) {
        discountDetails.merge(eventType, discount, Integer::sum);
    }
}
