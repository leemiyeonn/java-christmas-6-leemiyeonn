package christmas.exception;

import java.util.function.Supplier;

public interface ExceptionHandler<T> {
    T getResult(Supplier<T> supplier);
}
