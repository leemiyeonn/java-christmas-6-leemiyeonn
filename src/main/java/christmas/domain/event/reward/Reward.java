package christmas.domain.event.reward;

import christmas.domain.order.Order;

public interface Reward {
    Object evaluateReward(Order order);
}
