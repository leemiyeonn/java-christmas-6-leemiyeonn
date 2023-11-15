package christmas.exception;

public enum ExceptionMessage {
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_DATE_INPUT("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER_INPUT("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    BEVERAGE_ONLY_ORDER("음료만 주문 시, 주문할 수 없습니다."),
    QUANTITY_EXCEEDS_LIMIT("메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."),
    DUPLICATE_MENU_ITEM("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    INVALID_MENU_ITEM("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    INVALID_QUANTITY("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
