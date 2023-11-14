package christmas.domain.event.reward.strategy;

import christmas.domain.constants.event.reward.Gift;
import christmas.domain.event.reward.Reward;
import christmas.domain.order.Order;

public class GiftRewardStrategy implements Reward {
    private final int thresholdForChampagne;

    public GiftRewardStrategy(int thresholdForChampagne) {
        this.thresholdForChampagne = thresholdForChampagne;
    }

    @Override
    public Object evaluateReward(Order order) {
        int orderTotal = order.getOrderTotal();
        if (orderTotal >= thresholdForChampagne) {
            return Gift.CHAMPAGNE;
        }
        return null;
    }
}

