package christmas.domain.event.reward.strategy;

import christmas.domain.constants.event.reward.Badge;
import christmas.domain.event.reward.Reward;

public class BadgeRewardStrategy implements Reward {
    private final int thresholdForStar;
    private final int thresholdForTree;
    private final int thresholdForSanta;

    public BadgeRewardStrategy(int thresholdForStar, int thresholdForTree, int thresholdForSanta) {
        this.thresholdForStar = thresholdForStar;
        this.thresholdForTree = thresholdForTree;
        this.thresholdForSanta = thresholdForSanta;
    }

    @Override
    public Object evaluateReward(int benefitAmount) {

        if (benefitAmount >= thresholdForSanta) {
            return Badge.SANTA;
        }
        if (benefitAmount >= thresholdForTree) {
            return Badge.TREE;
        }
        if (benefitAmount >= thresholdForStar) {
            return Badge.STAR;
        }
        return null;
    }
}
