package christmas.view.constants;

public enum PrintFormat {
    WELCOME_MESSAGE("안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다."),
    ASK_FOR_DATE("%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"),
    ASK_FOR_ORDER("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"),
    EVENT_BENEFIT_PREVIEW("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),

    ORDER_MENU_HEADER("<주문 메뉴>\n"),
    ORDER_ITEM_FORMAT("%s %d개\n"),

    TOTAL_PRICE_BEFORE_DISCOUNT_HEADER("<할인 전 총주문 금액>\n"),
    TOTAL_PRICE_BEFORE_DISCOUNT_FORMAT("%,d원\n"),

    GIFT_MENU_HEADER("<증정 메뉴>\n"),
    GIFT_ITEM_FORMAT("%s %d개\n"),

    BENEFIT_DETAILS_HEADER("<혜택 내역>\n"),
    DISCOUNT_ITEM_FORMAT("%s: -%,d원\n"),

    TOTAL_BENEFIT_AMOUNT_HEADER("<총혜택 금액>\n"),
    TOTAL_BENEFIT_AMOUNT("-%,d원\n"),

    EXPECTED_PAYMENT_AFTER_DISCOUNT_HEADER("<할인 후 예상 결제 금액>\n"),
    EXPECTED_PAYMENT_AMOUNT("%,d원\n"),

    MONTHLY_EVENT_BADGE_HEADER("<%d월 이벤트 배지>\n"),
    MONTHLY_EVENT_BADGE("%s\n");

    private final String format;

    PrintFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... args) {
        return String.format(this.format, args);
    }
}
