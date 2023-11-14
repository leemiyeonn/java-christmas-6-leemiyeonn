package christmas.domain.event;

public class EventPeriod {
    private final int startDay;
    private final int endDay;

    public EventPeriod(int startDay, int endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public boolean isWithinPeriod(int dayOfMonth) {
        return dayOfMonth >= startDay && dayOfMonth <= endDay;
    }
}
