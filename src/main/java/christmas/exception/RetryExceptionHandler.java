package christmas.exception;

import christmas.utils.io.Printer;
import christmas.utils.validator.AbstractValidator;
import java.util.function.Supplier;

public class RetryExceptionHandler<T> implements ExceptionHandler<T> {
    private final AbstractValidator<T> validator;

    public RetryExceptionHandler(AbstractValidator<T> validator) {
        this.validator = validator;
    }

    @Override
    public T getResult(Supplier<T> supplier) {
        T result;
        while (true) {
            result = supplier.get();
            try {
                validator.validate(result);
                return result;
            } catch (IllegalArgumentException e) {
                Printer.printException(e);
            }
        }
    }
}