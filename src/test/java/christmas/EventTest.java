package christmas;

import static java.util.Map.entry;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import christmas.domain.constants.event.EventType;
import christmas.domain.constants.event.reward.Badge;
import christmas.domain.constants.event.reward.Gift;
import christmas.domain.menu.MenuItem;
import christmas.domain.order.FinalizedOrder;
import christmas.domain.order.Order;
import christmas.domain.order.OrderDiscounts;
import christmas.domain.order.OrderRewards;
import christmas.service.EventService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EventTest {
    private Order testOrder;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = new EventService();
    }

    @Nested
    class when_order_is_eligible_for_all_events_without_weekend {
        @BeforeEach
        void setUp() {
            int visitDay = 25;
            Map<MenuItem, Integer> items = Map.of(
                    MenuItem.MUSHROOM_SOUP, 2,
                    MenuItem.TBONE_STEAK, 1,
                    MenuItem.ICE_CREAM, 3,
                    MenuItem.RED_WINE, 4
            );
            testOrder = new Order(visitDay, items);
        }

        @Test
        void shouldApplyDiscountsCorrectly() {
            OrderDiscounts expectedDiscounts = new OrderDiscounts();
            expectedDiscounts.applyDiscount(EventType.CHRISTMAS_DISCOUNT, 3400);
            expectedDiscounts.applyDiscount(EventType.SPECIAL_DISCOUNT, 1000);
            expectedDiscounts.applyDiscount(EventType.WEEKDAY_DISCOUNT, 6069);
            expectedDiscounts.applyDiscount(EventType.WEEKEND_DISCOUNT, 0);

            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedDiscounts())
                    .usingRecursiveComparison()
                    .isEqualTo(expectedDiscounts);
        }

        @Test
        void shouldApplyRewardsCorrectly() {
            OrderRewards expectedRewards = new OrderRewards();
            expectedRewards.addGift(Gift.CHAMPAGNE);
            expectedRewards.addBadge(Badge.SANTA);

            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedRewards())
                    .usingRecursiveComparison()
                    .isEqualTo(expectedRewards);
        }

        @Test
        void shouldCalculateTotalBenefitsAmountCorrectly() {
            int expectedBenefits = 35_469;
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.calculateTotalBenefitsAmount()).isEqualTo(expectedBenefits);
        }

        @Test
        void shouldCalculateExpectedPaymentAfterDiscountCorrectly() {
            int expectedPayment = 311_531;
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getExpectedPaymentAfterDiscount()).isEqualTo(expectedPayment);
        }
    }

    @Nested
    class when_order_is_eligible_for_weekend_christmas {
        @BeforeEach
        void setUp() {
            int visitDay = 1;
            Map<MenuItem, Integer> items = Map.of(
                    MenuItem.BBQ_RIB, 1
            );
            testOrder = new Order(visitDay, items);
        }

        @Test
        void shouldApplyDiscountsCorrectly() {
            OrderDiscounts expectedDiscounts = new OrderDiscounts();
            expectedDiscounts.applyDiscount(EventType.CHRISTMAS_DISCOUNT, 1000);
            expectedDiscounts.applyDiscount(EventType.SPECIAL_DISCOUNT, 0);
            expectedDiscounts.applyDiscount(EventType.WEEKDAY_DISCOUNT, 0);
            expectedDiscounts.applyDiscount(EventType.WEEKEND_DISCOUNT, 2_023);

            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedDiscounts())
                    .usingRecursiveComparison()
                    .isEqualTo(expectedDiscounts);
        }

        @Test
        void shouldApplyRewardsCorrectly() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedRewards().getGifts()).isEmpty();
            assertThat(finalizedOrder.getAppliedRewards().getBadges()).isEmpty();
        }

        @Test
        void shouldCalculateTotalBenefitsAmountCorrectly() {
            int expectedBenefits = 3_023;
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.calculateTotalBenefitsAmount()).isEqualTo(expectedBenefits);
        }

        @Test
        void shouldCalculateExpectedPaymentAfterDiscountCorrectly() {
            int expectedPayment = 50_977;
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getExpectedPaymentAfterDiscount()).isEqualTo(expectedPayment);
        }
    }

    @Nested
    class when_order_is_ineligible_for_event_minimum_order_amount_not_met {
        @BeforeEach
        void setUp() {
            int visitDay = 25;
            Map<MenuItem, Integer> items = Map.of(
                    MenuItem.CAESAR_SALAD, 1
            );
            testOrder = new Order(visitDay, items);
        }

        @Test
        void shouldNotApplyAnyDiscounts() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedDiscounts().getDiscountDetails()).isEmpty();
        }

        @Test
        void shouldNotApplyAnyRewards() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedRewards().getGifts()).isEmpty();
            assertThat(finalizedOrder.getAppliedRewards().getBadges()).isEmpty();
        }

        @Test
        void shouldCalculateTotalBenefitsAmountAsZero() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.calculateTotalBenefitsAmount()).isZero();
        }

        @Test
        void shouldCalculateExpectedPaymentAfterDiscountEqualToOrderTotal() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getExpectedPaymentAfterDiscount()).isEqualTo(testOrder.calculateOrderTotal());
        }
    }

    @Nested
    class when_order_is_eligible_for_event_but_no_matching_event {
        @BeforeEach
        void setUp() {
            int visitDay = 26;
            Map<MenuItem, Integer> items = Map.of(
                    MenuItem.CAESAR_SALAD,1,
                    MenuItem.BBQ_RIB,1
            );
            testOrder = new Order(visitDay, items);
        }

        @Test
        void shouldNotApplyAnyDiscounts() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedDiscounts().getDiscountDetails())
                    .containsOnly(
                            entry(EventType.CHRISTMAS_DISCOUNT, 0),
                            entry(EventType.WEEKDAY_DISCOUNT, 0),
                            entry(EventType.WEEKEND_DISCOUNT, 0),
                            entry(EventType.SPECIAL_DISCOUNT, 0)
                    );
        }

        @Test
        void shouldNotApplyAnyRewards() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getAppliedRewards().getGifts()).isEmpty();
            assertThat(finalizedOrder.getAppliedRewards().getBadges()).isEmpty();
        }

        @Test
        void shouldCalculateTotalBenefitsAmountAsZero() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.calculateTotalBenefitsAmount()).isZero();
        }

        @Test
        void shouldCalculateExpectedPaymentAfterDiscountEqualToOrderTotal() {
            FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(testOrder);

            assertThat(finalizedOrder.getExpectedPaymentAfterDiscount()).isEqualTo(testOrder.calculateOrderTotal());
        }
    }
}
