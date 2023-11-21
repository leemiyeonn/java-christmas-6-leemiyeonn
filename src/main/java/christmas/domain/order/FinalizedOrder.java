package christmas.domain.order;

public class FinalizedOrder {
    private final Order originalOrder;
    private final OrderDiscounts appliedDiscounts;
    private final OrderRewards appliedRewards;

    public FinalizedOrder(Order originalOrder, OrderDiscounts appliedDiscounts, OrderRewards appliedRewards) {
        this.originalOrder = originalOrder;
        this.appliedDiscounts = appliedDiscounts;
        this.appliedRewards = appliedRewards;
    }

    public Order getOriginalOrder() {
        return originalOrder;
    }

    public OrderDiscounts getAppliedDiscounts() {
        return appliedDiscounts;
    }

    public OrderRewards getAppliedRewards() {
        return appliedRewards;
    }

    public int getExpectedPaymentAfterDiscount() {
        int orderTotal = originalOrder.calculateOrderTotal();
        int discountAmount = appliedDiscounts.getDiscountAmount();
        return orderTotal - discountAmount;
    }

    public int calculateTotalBenefitsAmount() {
        int discountAmount = appliedDiscounts.getDiscountAmount();
        int giftsTotalPrice = appliedRewards.getTotalGiftsPrice();
        return discountAmount + giftsTotalPrice;
    }
}
