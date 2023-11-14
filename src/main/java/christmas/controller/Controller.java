package christmas.controller;

import christmas.domain.order.Order;
import christmas.exception.ExceptionHandler;
import christmas.exception.RetryExceptionHandler;
import christmas.service.OrderService;
import christmas.utils.validator.DateValidator;
import christmas.utils.validator.OrderValidator;
import christmas.view.input.InputView;
import java.util.function.Supplier;

public class Controller {
    private final OrderService orderService;

    public Controller(OrderService orderService) {
        this.orderService = orderService;
    }

    public void run() {
        int visitDay = createEventDate();
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
        return orderService.createOrderFromInput(visitDay, validOrder);
    }
}
