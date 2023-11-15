package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import christmas.exception.RetryExceptionHandler;
import christmas.utils.validator.AbstractValidator;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RetryExceptionHandlerTest {
    private AbstractValidator validator;
    private Supplier supplier;
    private RetryExceptionHandler<String> retryExceptionHandler;

    @BeforeEach
    void setUp() {
        validator = mock(AbstractValidator.class);
        supplier = mock(Supplier.class);
        retryExceptionHandler = new RetryExceptionHandler<>(validator);
    }

    @Test
    void getResult_ShouldRetryUntilValidInput() {
        when(supplier.get()).thenReturn("invalid input1").thenReturn("invalid input2").thenReturn("valid input");

        doThrow(new IllegalArgumentException("Invalid input")).when(validator).validate("invalid input1");
        doThrow(new IllegalArgumentException("Invalid input")).when(validator).validate("invalid input2");

        String result = retryExceptionHandler.getResult(supplier);

        assertThat(result).isEqualTo("valid input");
        verify(supplier, times(3)).get();
        verify(validator).validate("invalid input1");
        verify(validator).validate("invalid input2");
        verify(validator).validate("valid input");
    }
}
