package christmas;

import christmas.controller.Controller;
import christmas.service.EventService;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new EventService());
        controller.run();
    }
}
