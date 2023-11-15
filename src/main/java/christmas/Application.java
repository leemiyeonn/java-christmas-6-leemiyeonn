package christmas;

import christmas.controller.Controller;
import christmas.service.EventService;
import christmas.service.OrderService;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new OrderService(), new EventService());
        controller.run();
    }
}
