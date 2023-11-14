package christmas.utils.validator;

import christmas.domain.constants.order.OrderConstants;
import christmas.domain.menu.MenuItem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator extends AbstractValidator<String> {
    private static String validPattern = "^(?:[가-힣]+-(?:[1-9]|[1-9][0-9]),\\s?)*[가-힣]+-(?:[1-9]|[1-9][0-9])$";

    @Override
    public void validate(String input) {
        validateInput(input);
        List<String> items = splitInput(input);
        validateItems(items);
    }

    private void validateInput(String input) {
        checkBlankOrNull(input);
        checkFormat(input);
    }

    private void checkBlankOrNull(String input) {
        if (isBlankOrNull(input)) {
            throw new IllegalArgumentException("The order input is blank or null.");
        }
    }

    private void checkFormat(String input) {
        if (!isValidInputFormat(input)) {
            throw new IllegalArgumentException("The order input format is invalid.");
        }
    }

    private boolean isValidInputFormat(String input) {
        Pattern pattern = Pattern.compile(validPattern);
        Matcher matcher = pattern.matcher(input.trim());

        return matcher.matches();
    }

    private List<String> splitInput(String input) {
        return Arrays.asList(input.split(",\\s*"));
    }

    private void validateItems(List<String> items) {
        checkEachItem(items);
        checkUniqueItems(items);
        checkTotalItemCount(items);
    }

    private void checkEachItem(List<String> items) {
        for (String item : items) {
            List<String> details = Arrays.asList(item.split("-"));
            checkOrderItemFormat(details);
            checkMenuItem(details.get(0));
        }

    }

    private void checkOrderItemFormat(List<String> details) {
        checkOrderDetailsFormat(details);
        checkQuantity(details.get(1));
    }

    private void checkOrderDetailsFormat(List<String> orderDetails) {
        if (orderDetails.size() != 2) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void checkQuantity(String quantityStr) {
        if (!isQuantityValid(quantityStr)) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private boolean isQuantityValid(String quantityStr) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return quantity > 0;
    }

    private void checkUniqueItems(List<String> items) {
        if (!areMenuItemsUnique(items)) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private boolean areMenuItemsUnique(List<String> items) {
        Set<String> uniqueItems = new HashSet<>(items);
        return uniqueItems.size() == items.size();
    }

    private void checkTotalItemCount(List<String> items) {
        if (!isTotalItemCountValid(items)) {
            throw new IllegalArgumentException("The total number of items is greater than allowed.");
        }
    }

    private boolean isTotalItemCountValid(List<String> items) {
        int totalQuantity = items.stream()
                .map(this::extractQuantityFromDetail)
                .mapToInt(Integer::intValue)
                .sum();
        return totalQuantity <= OrderConstants.MAX_MENU_ITEMS_PER_ORDER.getValue();
    }

    private int extractQuantityFromDetail(String itemDetail) {
        String[] parts = itemDetail.split("-");
        if(parts.length != 2) {
            return 0;
        }
        return Integer.parseInt(parts[1].trim());
    }

    private void checkMenuItem(String menuItem) {
        if (!isMenuItemValid(menuItem)) {
            throw new IllegalArgumentException("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private boolean isMenuItemValid(String menuItemName) {
        return MenuItem.from(menuItemName) != null;
    }
}
