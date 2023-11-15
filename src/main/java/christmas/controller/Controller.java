package christmas.controller;

import christmas.domain.menu.MenuItem;
import christmas.domain.order.FinalizedOrder;
import christmas.domain.order.Order;
import christmas.exception.ExceptionHandler;
import christmas.exception.RetryExceptionHandler;
import christmas.service.EventService;
import christmas.service.OrderService;
import christmas.utils.validator.DateValidator;
import christmas.utils.validator.OrderValidator;
import christmas.view.input.InputView;
import christmas.view.output.OutputView;
import java.util.Map;
import java.util.function.Supplier;

public class Controller {
    private final OrderService orderService;
    private final EventService eventService;

    public Controller(OrderService orderService, EventService eventService) {
        this.orderService = orderService;
        this.eventService = eventService;
    }

    public void run() {
        OutputView.printStart();

        int visitDay = createEventDate();
        Order originalOrder = createOrder(visitDay);

        FinalizedOrder finalizedOrder = eventService.applyEventDiscountsAndRewards(originalOrder);

        OutputView.printResults(originalOrder, finalizedOrder);
    }

    private int createEventDate() {
        ExceptionHandler<String> retryHandler = new RetryExceptionHandler<>(new DateValidator());
        Supplier<String> dateInputSupplier = InputView::readDate;
        String validDate = retryHandler.getResult(dateInputSupplier);
        return processValidDate(validDate);
    }

    private int processValidDate(String validDate) {
        return Integer.parseInt(validDate);
    }

    public Order createOrder(int visitDay) {
        ExceptionHandler<String> retryHandler = new RetryExceptionHandler<>(new OrderValidator());
        Supplier<String> orderInputSupplier = InputView::readOrder;
        String validOrder = retryHandler.getResult(orderInputSupplier);
        return processValidOrder(visitDay, validOrder);
    }

    private Order processValidOrder(int visitDay, String validOrder) {
        Map<MenuItem, Integer> orderDetails = orderService.parseOrderDetails(validOrder);
        return new Order(visitDay, orderDetails);
    }
}
