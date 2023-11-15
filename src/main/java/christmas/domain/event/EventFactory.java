package christmas.domain.event;

import christmas.domain.constants.event.EventConstants;
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

        EventPeriod christmasEventPeriod = new EventPeriod(EventConstants.CHRISTMAS_EVENT_START_DAY.getValue(), EventConstants.CHRISTMAS_EVENT_END_DAY.getValue());
        EventPeriod decemberEventPeriod = new EventPeriod(EventConstants.DECEMBER_FIRST_DAY.getValue(), EventConstants.DECEMBER_LAST_DAY.getValue());

        Set<Integer> weekdayIndex = Set.of(
                EventConstants.SUNDAY_INDEX.getValue(),
                EventConstants.MONDAY_INDEX.getValue(),
                EventConstants.TUESDAY_INDEX.getValue(),
                EventConstants.WEDNESDAY_INDEX.getValue(),
                EventConstants.THURSDAY_INDEX.getValue()
        );

        Set<Integer> weekendIndex = Set.of(
                EventConstants.FRIDAY_INDEX.getValue(),
                EventConstants.SATURDAY_INDEX.getValue()
        );

        Set<Integer> specialDates = Set.of(
                EventConstants.SPECIAL_DATE_ONE.getValue(),
                EventConstants.SPECIAL_DATE_TWO.getValue(),
                EventConstants.SPECIAL_DATE_THREE.getValue(),
                EventConstants.SPECIAL_DATE_FOUR.getValue(),
                EventConstants.SPECIAL_DATE_FIVE.getValue(),
                EventConstants.SPECIAL_DATE_SIX.getValue()
        );

        events.add(new Event(EventType.CHRISTMAS_DISCOUNT, christmasEventPeriod,
                new ChristmasDiscountStrategy(
                        EventConstants.CHRISTMAS_EVENT_START_DAY.getValue(),
                        EventConstants.CHRISTMAS_DISCOUNT_START_AMOUNT.getValue(),
                        EventConstants.CHRISTMAS_DISCOUNT_INCREMENT.getValue()), null));

        events.add(new Event(EventType.WEEKDAY_DISCOUNT, decemberEventPeriod,
                new DayOfWeekDiscountStrategy(MenuCategory.DESSERT, EventConstants.WEEKDAY_DISCOUNT_AMOUNT.getValue(),
                        weekdayIndex),
                null));

        events.add(new Event(EventType.WEEKEND_DISCOUNT, decemberEventPeriod,
                new DayOfWeekDiscountStrategy(MenuCategory.MAIN, EventConstants.WEEKEND_DISCOUNT_AMOUNT.getValue(),
                        weekendIndex),
                null));

        events.add(new Event(EventType.SPECIAL_DISCOUNT, decemberEventPeriod,
                new SpecialDiscountStrategy(EventConstants.SPECIAL_DISCOUNT_AMOUNT.getValue(), specialDates), null));

        events.add(new Event(EventType.GIFT_REWARD, decemberEventPeriod,
                null, new GiftRewardStrategy(EventConstants.GIFT_REWARD_CHAMPAGNE_THRESHOLD.getValue())));

        events.add(new Event(EventType.BADGE_REWARD, decemberEventPeriod, null,
                new BadgeRewardStrategy(EventConstants.BADGE_REWARD_STAR_THRESHOLD.getValue(),
                        EventConstants.BADGE_REWARD_TREE_THRESHOLD.getValue(),
                        EventConstants.BADGE_REWARD_SANTA_THRESHOLD.getValue())));

        return events;
    }
}
