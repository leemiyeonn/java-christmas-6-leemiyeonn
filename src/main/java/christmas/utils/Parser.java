package christmas.utils;

import christmas.domain.menu.MenuItem;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Parser {
    private Parser() {
    }

    public static int parseInteger(String input, String errorMessage) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static Map<MenuItem, Integer> parseOrderDetails(String orderInput) {
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
}
