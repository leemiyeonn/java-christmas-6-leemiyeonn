package christmas.domain.event.reward.strategy;

import christmas.domain.constants.event.reward.Gift;
import christmas.domain.event.reward.Reward;

public class GiftRewardStrategy implements Reward {
    private final int thresholdForChampagne;

    public GiftRewardStrategy(int thresholdForChampagne) {
        this.thresholdForChampagne = thresholdForChampagne;
    }

    @Override
    public Object evaluateReward(int orderTotal) {
        if (orderTotal >= thresholdForChampagne) {
            return Gift.CHAMPAGNE;
        }
        return null;
    }
}

