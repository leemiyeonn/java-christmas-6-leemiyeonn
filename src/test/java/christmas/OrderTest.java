package christmas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.controller.Controller;
import christmas.domain.menu.MenuItem;
import christmas.domain.order.Order;
import christmas.service.EventService;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Test
    void testProcessValidDate() {
        Controller controller = new Controller(new EventService());
        int visitDay = controller.processValidDate("31");
        assertEquals(31, visitDay);
    }

    @Test
    void testProcessValidOrder() {
        Controller controller = new Controller(new EventService());
        String validOrder = "티본스테이크-1,아이스크림-2,레드와인-3";
        Order order = controller.processValidOrder(31, validOrder);

        assertEquals(1, order.getOrderDetails().get(MenuItem.TBONE_STEAK).intValue());
        assertEquals(2, order.getOrderDetails().get(MenuItem.ICE_CREAM).intValue());
        assertEquals(3, order.getOrderDetails().get(MenuItem.RED_WINE).intValue());
    }
}
