package christmas.domain.order;

import christmas.domain.menu.MenuItem;
import java.util.Collections;
import java.util.Map;

public class Order {
    private final int visitDay;
    private final Map<MenuItem, Integer> items;

    public Order(int visitDay, Map<MenuItem, Integer> items) {
        this.visitDay = visitDay;
        this.items = items;
    }

    public int getVisitDay() {
        return visitDay;
    }

    public Map<MenuItem, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public int calculateOrderTotal() {
        return items.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
