package christmas.view.output;

import christmas.domain.constants.event.EventConstants;
import christmas.domain.constants.event.EventType;
import christmas.domain.constants.event.reward.Gift;
import christmas.domain.order.Order;
import christmas.utils.io.Printer;
import christmas.view.constants.PrintFormat;
import java.util.Map;

public class OutputView {
    public static void printStart() {
        Printer.printFormattedMessage(PrintFormat.WELCOME_MESSAGE, EventConstants.DECEMBER.getValue());
    }

    public static void printResults(Order order) {
        Printer.printFormattedMessage(PrintFormat.EVENT_BENEFIT_PREVIEW, EventConstants.DECEMBER.getValue(), order.getVisitDay());
        Printer.printSeparator();
        printResult(order);
    }

    private static void printResult(Order order) {
        printMenu(order);
        printOrderTotal(order);
        printGifts(order);
        printBenefitDetails(order);
        printTotalBenefitAmount(order);
        printExpectedPayment(order);
        printMonthlyEventBadge(order);
    }

    private static void printMenu(Order order) {
        Printer.printFormattedMessage(PrintFormat.ORDER_MENU_HEADER);

        order.getItems().forEach((menuItem, quantity) ->
                Printer.printFormattedMessage(PrintFormat.ORDER_ITEM_FORMAT, menuItem.getKoreanName(), quantity));
        Printer.printSeparator();
    }

    private static void printOrderTotal(Order order) {
        Printer.printFormattedMessage(PrintFormat.TOTAL_PRICE_BEFORE_DISCOUNT_HEADER);

        Printer.printFormattedMessage(PrintFormat.TOTAL_PRICE_BEFORE_DISCOUNT_FORMAT, order.getOrderTotal());
        Printer.printSeparator();
    }

    private static void printGifts(Order order) {
        Printer.printFormattedMessage(PrintFormat.GIFT_MENU_HEADER);

        if (order.getGifts().isEmpty()) {
            Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_NONE);
            Printer.printSeparator();
            return;
        }
        order.getGifts().forEach(gift ->
                Printer.printFormattedMessage(PrintFormat.GIFT_ITEM_FORMAT, gift.getPrintFormat(), 1));
        Printer.printSeparator();
    }

    public static void printBenefitDetails(Order order) {
        Printer.printMessage(PrintFormat.BENEFIT_DETAILS_HEADER);
        boolean hasDiscounts = printDiscountDetails(order);
        boolean hasGifts = printGiftDetails(order);
        if (!hasDiscounts && !hasGifts) {
            Printer.printMessage(PrintFormat.BENEFIT_DETAILS_NONE);
        }
        Printer.printSeparator();
    }

    private static boolean printDiscountDetails(Order order) {
        boolean hasDiscounts = false;
        for (Map.Entry<EventType, Integer> entry : order.getDiscountDetails().entrySet()) {
            if (entry.getValue() > 0) {
                String discountPrintFormat = entry.getKey().getPrintFormat();
                int discountAmount = entry.getValue();
                Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_FORMAT, discountPrintFormat, discountAmount);
                hasDiscounts = true;
            }
        }
        return hasDiscounts;
    }

    private static boolean printGiftDetails(Order order) {
        boolean hasGifts = false;
        if (!order.getGifts().isEmpty()) {
            for (Gift gift : order.getGifts()) {
                String rewardPrintFormat = EventType.GIFT_REWARD.getPrintFormat();
                int giftPrice = gift.getPrice();
                Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_FORMAT, rewardPrintFormat, giftPrice);
                hasGifts = true;
            }
        }
        return hasGifts;
    }

    private static void printTotalBenefitAmount(Order order) {
        Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_AMOUNT_HEADER);

        if (order.calculateTotalBenefitAmount() == 0) {
            Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_ZERO_AMOUNT);
            Printer.printSeparator();
            return;
        }
        Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_AMOUNT, order.calculateTotalBenefitAmount());
        Printer.printSeparator();
    }

    private static void printExpectedPayment(Order order) {
        Printer.printFormattedMessage(PrintFormat.EXPECTED_PAYMENT_AFTER_DISCOUNT_HEADER);

        Printer.printFormattedMessage(PrintFormat.EXPECTED_PAYMENT_AMOUNT, order.getExpectedPaymentAfterDiscount());
        Printer.printSeparator();
    }

    private static void printMonthlyEventBadge(Order order) {
        Printer.printFormattedMessage(PrintFormat.MONTHLY_EVENT_BADGE_HEADER, EventConstants.DECEMBER.getValue());

        if (order.getBadges().isEmpty()) {
            Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_NONE);
            Printer.printSeparator();
            return;
        }
        order.getBadges().forEach(badge ->
                Printer.printFormattedMessage(PrintFormat.MONTHLY_EVENT_BADGE, badge.getPrintFormat()));
        Printer.printSeparator();
    }
}
