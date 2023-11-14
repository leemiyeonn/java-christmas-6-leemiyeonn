package christmas.domain.constants.event;

public enum EventConstants {
    DECEMBER(12),
    DECEMBER_FIRST_DAY(1),
    DECEMBER_LAST_DAY(31),
    DECEMBER_FIRST_DAY_OF_WEEK_INDEX(5),

    GENERAL_EVENT_START_DAY(1),
    GENERAL_EVENT_END_DAY(31),
    CHRISTMAS_EVENT_START_DAY(1),
    CHRISTMAS_EVENT_END_DAY(25);

    private final int value;

    EventConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
