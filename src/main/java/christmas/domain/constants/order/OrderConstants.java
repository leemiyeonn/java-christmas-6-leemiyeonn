package christmas.domain.constants.order;

public enum OrderConstants {
    MIN_ORDER_AMOUNT_FOR_EVENT(10_000),
    MAX_MENU_ITEMS_PER_ORDER(20);

    private final int value;

    OrderConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
