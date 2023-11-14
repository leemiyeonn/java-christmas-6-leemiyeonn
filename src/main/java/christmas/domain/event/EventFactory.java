package christmas.domain.event;

import christmas.domain.constants.event.EventType;
import christmas.domain.event.discount.strategy.ChristmasDiscountStrategy;
import christmas.domain.event.discount.strategy.DayOfWeekDiscountStrategy;
import christmas.domain.event.discount.strategy.SpecialDiscountStrategy;
import christmas.domain.event.reward.strategy.BadgeRewardStrategy;
import christmas.domain.event.reward.strategy.GiftRewardStrategy;
import christmas.domain.menu.MenuCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventFactory {
    public static List<Event> createEvents() {
        List<Event> events = new ArrayList<>();

        EventPeriod christmasEventPeriod = new EventPeriod(1, 25);
        EventPeriod decemberEventPeriod = new EventPeriod(1, 31);

        Set<Integer> weekdayIndex = Set.of(0,1,2,3,4);
        Set<Integer> weekendIndex = Set.of(5,6);

        Set<Integer> specialDates = Set.of(3,10,17,24,25,31);

        events.add(new Event(EventType.CHRISTMAS_DISCOUNT, christmasEventPeriod,
                new ChristmasDiscountStrategy(1, 1_000, 100),null));

        events.add(new Event(EventType.WEEKDAY_DISCOUNT, decemberEventPeriod,
                new DayOfWeekDiscountStrategy(MenuCategory.DESSERT, 2_023, weekdayIndex),
                null));

        events.add(new Event(EventType.WEEKEND_DISCOUNT, decemberEventPeriod,
                new DayOfWeekDiscountStrategy(MenuCategory.MAIN, 2_023, weekendIndex),
                null));

        events.add(new Event(EventType.SPECIAL_DISCOUNT, decemberEventPeriod,
                new SpecialDiscountStrategy(1_000, specialDates),null));

        events.add(new Event(EventType.GIFT_REWARD, decemberEventPeriod,
                null, new GiftRewardStrategy(120_000)));

        events.add(new Event(EventType.BADGE_REWARD, decemberEventPeriod,
                null, new BadgeRewardStrategy(5_000, 10_000, 20_000)));

        return events;
    }
}
