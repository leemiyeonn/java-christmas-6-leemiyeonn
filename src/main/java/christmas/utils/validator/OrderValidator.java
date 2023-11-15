package christmas.utils.validator;

import christmas.domain.constants.order.OrderConstants;
import christmas.domain.menu.MenuCategory;
import christmas.domain.menu.MenuItem;
import christmas.exception.ExceptionMessage;
import christmas.utils.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class OrderValidator extends AbstractValidator<String> {
    private static final String VALID_PATTERN = "^(?:[가-힣]+-(?:[1-9]|[1-9][0-9]),\\s?)*[가-힣]+-(?:[1-9]|[1-9][0-9])$";

    @Override
    public void validate(String input) {
        validateInput(input);
        List<String> items = splitInput(input);
        validateItems(items);
    }

    private void validateInput(String input) {
        if (isBlankOrNull(input)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }
        if (!isValidInputFormat(input)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }

    private boolean isValidInputFormat(String input) {
        return Pattern.matches(VALID_PATTERN, input.trim());
    }

    private List<String> splitInput(String input) {
        return Arrays.asList(input.split(",\\s*"));
    }

    private void validateItems(List<String> items) {
        Set<String> uniqueMenuNames = new HashSet<>();
        List<MenuItem> menuItemsList = new ArrayList<>();
        int totalQuantity = 0;

        for (String item : items) {
            validateItem(item, uniqueMenuNames, menuItemsList);
            totalQuantity += parseQuantity(item.split("-")[1]);
        }

        validateBeverageOnlyOrder(menuItemsList);
        validateTotalQuantity(totalQuantity);
    }

    private void validateItem(String item, Set<String> uniqueMenuNames, List<MenuItem> menuItemsList) {
        List<String> parts = parseItemParts(item);
        String menuItemName = parts.get(0);
        int quantity = parseQuantity(parts.get(1));

        validateQuantity(quantity);
        validateUniqueMenuItem(uniqueMenuNames, menuItemName);
        MenuItem menuItem = validateMenuItemExists(menuItemName);

        menuItemsList.add(menuItem);
    }

    private List<String> parseItemParts(String item) {
        List<String> parts = new ArrayList<>(Arrays.asList(item.trim().split("-")));
        if (parts.size() != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }
        return parts;
    }

    private int parseQuantity(String quantityStr) {
        return Parser.parseInteger(quantityStr, ExceptionMessage.INVALID_QUANTITY.getMessage());
    }

    private MenuItem validateMenuItemExists(String menuItemName) {
        MenuItem menuItem = MenuItem.from(menuItemName);
        if (menuItem == null) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_MENU_ITEM.getMessage());
        }
        return menuItem;
    }

    private void validateUniqueMenuItem(Set<String> uniqueMenuNames, String menuItemName) {
        if (!uniqueMenuNames.add(menuItemName)) {
            throw new IllegalArgumentException(ExceptionMessage.DUPLICATE_MENU_ITEM.getMessage());
        }
    }

    private void validateBeverageOnlyOrder(List<MenuItem> menuItems) {
        boolean allBeverageItems = menuItems.stream().allMatch(this::isMenuItemBeverage);
        if (allBeverageItems) {
            throw new IllegalArgumentException(ExceptionMessage.BEVERAGE_ONLY_ORDER.getMessage());
        }
    }

    private boolean isMenuItemBeverage(MenuItem item) {
        return item.getCategory() == MenuCategory.BEVERAGE;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_QUANTITY.getMessage());
        }
    }

    private void validateTotalQuantity(int totalQuantity) {
        if (totalQuantity > OrderConstants.MAX_MENU_ITEMS_PER_ORDER.getValue()) {
            throw new IllegalArgumentException(ExceptionMessage.QUANTITY_EXCEEDS_LIMIT.getMessage());
        }
    }
}
