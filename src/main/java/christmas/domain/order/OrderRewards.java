package christmas.domain.order;

import christmas.domain.constants.event.reward.Badge;
import christmas.domain.constants.event.reward.Gift;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class OrderRewards {
    private final Set<Gift> gifts;
    private final Set<Badge> badges;

    public OrderRewards() {
        this.gifts = new LinkedHashSet<>();
        this.badges = new LinkedHashSet<>();
    }

    public Set<Gift> getGifts() {
        return Collections.unmodifiableSet(gifts);
    }

    public Set<Badge> getBadges() {
        return Collections.unmodifiableSet(badges);
    }

    public int getTotalGiftsPrice() {
        return gifts.stream()
                .mapToInt(Gift::getPrice)
                .sum();
    }

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public void addBadge(Badge badge) {
        badges.add(badge);
    }
}
