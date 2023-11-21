package christmas.domain.order;

import christmas.domain.menu.MenuItem;
import java.util.Collections;
import java.util.Map;

public class Order {
    private final int visitDay;
    private final Map<MenuItem, Integer> orderDetails;

    public Order(int visitDay, Map<MenuItem, Integer> orderDetails) {
        this.visitDay = visitDay;
        this.orderDetails = orderDetails;
    }

    public int getVisitDay() {
        return visitDay;
    }

    public Map<MenuItem, Integer> getOrderDetails() {
        return Collections.unmodifiableMap(orderDetails);
    }

    public int calculateOrderTotal() {
        return orderDetails.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
