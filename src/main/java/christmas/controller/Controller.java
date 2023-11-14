package christmas.controller;

import christmas.exception.ExceptionHandler;
import christmas.exception.RetryExceptionHandler;
import christmas.utils.validator.DateValidator;
import christmas.view.input.InputView;
import java.util.function.Supplier;

public class Controller {
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
}
