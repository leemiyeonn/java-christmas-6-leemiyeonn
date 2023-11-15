package christmas.view.output;

import christmas.domain.constants.event.EventConstants;
import christmas.domain.constants.event.EventType;
import christmas.domain.constants.event.reward.Gift;
import christmas.domain.order.FinalizedOrder;
import christmas.domain.order.Order;
import christmas.utils.io.Printer;
import christmas.view.constants.PrintFormat;
import java.util.Map;

public class OutputView {
    public static void printStart() {
        Printer.printFormattedMessage(PrintFormat.WELCOME_MESSAGE, EventConstants.DECEMBER.getValue());
    }

    public static void printResults(Order originalOrder, FinalizedOrder finalizedOrder) {
        Printer.printFormattedMessage(PrintFormat.EVENT_BENEFIT_PREVIEW, EventConstants.DECEMBER.getValue(), originalOrder.getVisitDay());
        Printer.printSeparator();
        printResult(finalizedOrder);
    }

    private static void printResult(FinalizedOrder finalizedOrder) {
        printMenu(finalizedOrder.getOriginalOrder());
        printOrderTotal(finalizedOrder.getOriginalOrder());
        printGifts(finalizedOrder);
        printBenefitDetails(finalizedOrder);
        printTotalBenefitAmount(finalizedOrder);
        printExpectedPayment(finalizedOrder);
        printMonthlyEventBadge(finalizedOrder);
    }

    private static void printMenu(Order originalOrder) {
        Printer.printFormattedMessage(PrintFormat.ORDER_MENU_HEADER);

        originalOrder.getOrderDetails().forEach((menuItem, quantity) ->
                Printer.printFormattedMessage(PrintFormat.ORDER_ITEM_FORMAT, menuItem.getKoreanName(), quantity));
        Printer.printSeparator();
    }

    private static void printOrderTotal(Order originalOrder) {
        Printer.printFormattedMessage(PrintFormat.TOTAL_PRICE_BEFORE_DISCOUNT_HEADER);

        Printer.printFormattedMessage(PrintFormat.TOTAL_PRICE_BEFORE_DISCOUNT_FORMAT, originalOrder.calculateOrderTotal());
        Printer.printSeparator();
    }

    private static void printGifts(FinalizedOrder finalizedOrder) {
        Printer.printFormattedMessage(PrintFormat.GIFT_MENU_HEADER);

        if (finalizedOrder.getAppliedRewards().getGifts().isEmpty()) {
            Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_NONE);
            Printer.printSeparator();
            return;
        }

        finalizedOrder.getAppliedRewards().getGifts()
                .forEach(gift -> Printer.printFormattedMessage(PrintFormat.GIFT_ITEM_FORMAT, gift.getPrintFormat(), 1));
        Printer.printSeparator();
    }

    public static void printBenefitDetails(FinalizedOrder finalizedOrder) {
        Printer.printMessage(PrintFormat.BENEFIT_DETAILS_HEADER);

        boolean hasDiscounts = printDiscountDetails(finalizedOrder);
        boolean hasGifts = printGiftDetails(finalizedOrder);
        if (!hasDiscounts && !hasGifts) {
            Printer.printMessage(PrintFormat.BENEFIT_DETAILS_NONE);
        }
        Printer.printSeparator();
    }

    private static boolean printDiscountDetails(FinalizedOrder finalizedOrder) {
        boolean hasDiscounts = false;
        for (Map.Entry<EventType, Integer> entry : finalizedOrder.getAppliedDiscounts().getDiscountDetails().entrySet()) {
            if (entry.getValue() > 0) {
                String discountPrintFormat = entry.getKey().getPrintFormat();
                int discountAmount = entry.getValue();
                Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_FORMAT, discountPrintFormat, discountAmount);
                hasDiscounts = true;
            }
        }
        return hasDiscounts;
    }

    private static boolean printGiftDetails(FinalizedOrder finalizedOrder) {
        boolean hasGifts = false;
        if (!finalizedOrder.getAppliedRewards().getGifts().isEmpty()) {
            for (Gift gift : finalizedOrder.getAppliedRewards().getGifts()) {
                String rewardPrintFormat = EventType.GIFT_REWARD.getPrintFormat();
                int giftPrice = gift.getPrice();
                Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_FORMAT, rewardPrintFormat, giftPrice);
                hasGifts = true;
            }
        }
        return hasGifts;
    }

    private static void printTotalBenefitAmount(FinalizedOrder finalizedOrder) {
        Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_AMOUNT_HEADER);

        if (finalizedOrder.calculateTotalBenefitsAmount() == 0) {
            Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_ZERO_AMOUNT);
            Printer.printSeparator();
            return;
        }
        Printer.printFormattedMessage(PrintFormat.TOTAL_BENEFIT_AMOUNT, finalizedOrder.calculateTotalBenefitsAmount());
        Printer.printSeparator();
    }

    private static void printExpectedPayment(FinalizedOrder finalizedOrder) {
        Printer.printFormattedMessage(PrintFormat.EXPECTED_PAYMENT_AFTER_DISCOUNT_HEADER);

        Printer.printFormattedMessage(PrintFormat.EXPECTED_PAYMENT_AMOUNT, finalizedOrder.getExpectedPaymentAfterDiscount());
        Printer.printSeparator();
    }

    private static void printMonthlyEventBadge(FinalizedOrder finalizedOrder) {
        Printer.printFormattedMessage(PrintFormat.MONTHLY_EVENT_BADGE_HEADER, EventConstants.DECEMBER.getValue());

        if (finalizedOrder.getAppliedRewards().getBadges().isEmpty()) {
            Printer.printFormattedMessage(PrintFormat.BENEFIT_DETAILS_NONE);
            Printer.printSeparator();
            return;
        }
        finalizedOrder.getAppliedRewards().getBadges().forEach(badge ->
                Printer.printFormattedMessage(PrintFormat.MONTHLY_EVENT_BADGE, badge.getPrintFormat()));
        Printer.printSeparator();
    }
}
