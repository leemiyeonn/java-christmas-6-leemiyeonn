package christmas.domain.constants.event;

public enum EventType {
    CHRISTMAS_DISCOUNT("크리스마스 디데이 할인"),
    WEEKDAY_DISCOUNT("평일 할인"),
    WEEKEND_DISCOUNT("주말 할인"),
    SPECIAL_DISCOUNT("특별 할인"),
    GIFT_REWARD("증정 이벤트"),
    BADGE_REWARD("12월 이벤트 배지");

    private final String printFormat;

    EventType(String printFormat) {
        this.printFormat = printFormat;
    }

    public String getPrintFormat() {
        return printFormat;
    }
}
