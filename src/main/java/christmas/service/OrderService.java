package christmas.service;

import christmas.domain.order.Order;
import christmas.domain.menu.MenuItem;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OrderService {
    public Order createOrderFromInput(int visitDay, String validOrderInput) {
        Map<MenuItem, Integer> orderDetails = parseOrderDetails(validOrderInput);
        int totalOrderPrice = calculateTotalPrice(orderDetails);

        return new Order(visitDay, orderDetails, totalOrderPrice);
    }

    private Map<MenuItem, Integer> parseOrderDetails(String orderInput) {
        List<String> orderedItems = Stream.of(orderInput.split(","))
                .map(String::trim)
                .toList();

        EnumMap<MenuItem, Integer> orderDetails = new EnumMap<>(MenuItem.class);

        for (String item : orderedItems) {
            List<String> itemParts = Stream.of(item.split("-"))
                    .map(String::trim)
                    .toList();

            String menuItemName = itemParts.get(0);
            MenuItem menuItem = MenuItem.from(menuItemName);

            int quantity = Integer.parseInt(itemParts.get(1));
            orderDetails.put(menuItem, quantity);
        }

        return orderDetails;
    }

    private int calculateTotalPrice(Map<MenuItem, Integer> orderDetails) {
        return orderDetails.entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
