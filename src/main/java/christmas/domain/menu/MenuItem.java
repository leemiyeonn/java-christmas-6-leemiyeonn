package christmas.domain.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MenuItem {

    /** 에피타이저 */
    MUSHROOM_SOUP("양송이수프", 6_000, MenuCategory.APPETIZER),
    TAPAS("타파스", 5_500, MenuCategory.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, MenuCategory.APPETIZER),

    /** 메인 */
    TBONE_STEAK("티본스테이크", 55_000, MenuCategory.MAIN),
    BBQ_RIB("바비큐립", 54_000, MenuCategory.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, MenuCategory.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, MenuCategory.MAIN),

    /** 디저트 */
    CHOCOLATE_CAKE("초코케이크", 15_000, MenuCategory.DESSERT),
    ICE_CREAM("아이스크림", 5_000, MenuCategory.DESSERT),

    /** 음료 */
    ZERO_COLA("제로콜라", 3_000, MenuCategory.BEVERAGE),
    RED_WINE("레드와인", 60_000, MenuCategory.BEVERAGE),
    CHAMPAGNE("샴페인", 25_000, MenuCategory.BEVERAGE);

    private final String koreanName;
    private final int price;
    private final MenuCategory category;

    MenuItem(String koreanName, int price, MenuCategory category) {
        this.koreanName = koreanName;
        this.price = price;
        this.category = category;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getCategory() {
        return category;
    }

    private static final Map<String, MenuItem> koreanNameToMenuItemMap = new HashMap<>();
    private static final Map<MenuCategory, List<MenuItem>> menuByCategory = new EnumMap<>(MenuCategory.class);

    static {
        for (MenuItem item : MenuItem.values()) {
            koreanNameToMenuItemMap.put(item.getKoreanName(), item);
            menuByCategory.computeIfAbsent(item.category, k -> new ArrayList<>()).add(item);
        }
    }

    public static MenuItem from(String koreanName) {
        return koreanNameToMenuItemMap.get(koreanName);
    }

    public static List<MenuItem> getItemsByCategory(MenuCategory category) {
        return menuByCategory.getOrDefault(category, Collections.emptyList());
    }
}
