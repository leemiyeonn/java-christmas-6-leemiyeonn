package christmas;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ExceptionMessage;
import christmas.utils.validator.DateValidator;
import christmas.utils.validator.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
    @Nested
    class DateValidatorTests {
        private DateValidator dateValidator;

        @BeforeEach
        void setUp() {
            dateValidator = new DateValidator();
        }

        @Test
        void shouldNotThrowException_WhenInputIsValid() {
            assertThatCode(() -> dateValidator.validate("15"))
                    .doesNotThrowAnyException();
        }

        @Test
        void shouldThrowException_WhenInputIsNull() {
            assertThatThrownBy(() -> dateValidator.validate(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_DATE_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenInputIsEmpty() {
            assertThatThrownBy(() -> dateValidator.validate(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_DATE_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenInputIsNotNumeric() {
            assertThatThrownBy(() -> dateValidator.validate("abcd"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_DATE.getMessage());
        }

        @Test
        void shouldThrowException_WhenDayIsInvalid() {
            assertThatThrownBy(() -> dateValidator.validate("32"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_DATE.getMessage());
        }
    }

    @Nested
    class OrderValidatorTests {
        private OrderValidator orderValidator;

        @BeforeEach
        void setUp() {
            orderValidator = new OrderValidator();
        }

        @Test
        void shouldNotThrowException_WhenInputIsValid() {
            assertThatCode(() -> orderValidator.validate("초코케이크-1, 티본스테이크-2"))
                    .doesNotThrowAnyException();
        }

        @Test
        void shouldThrowException_WhenInputIsEmpty() {
            assertThatThrownBy(() -> orderValidator.validate(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenInputIsNull() {
            assertThatThrownBy(() -> orderValidator.validate(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenMenuItemIsDuplicated() {
            assertThatThrownBy(() -> orderValidator.validate("티본스테이크-1, 티본스테이크-2"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.DUPLICATE_MENU_ITEM.getMessage());
        }

        @Test
        void shouldThrowException_WhenOrderIsBeverageOnly() {
            assertThatThrownBy(() -> orderValidator.validate("제로콜라-1, 샴페인-2"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.BEVERAGE_ONLY_ORDER.getMessage());
        }

        @Test
        void shouldThrowException_WhenQuantityExceedsLimit() {
            assertThatThrownBy(() -> orderValidator.validate("바비큐립-10, 초코케이크-10, 양송이수프-1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.QUANTITY_EXCEEDS_LIMIT.getMessage());
        }

        @Test
        void shouldThrowException_WhenItemIsNotOnMenu() {
            assertThatThrownBy(() -> orderValidator.validate("피자-1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_MENU_ITEM.getMessage());
        }

        @Test
        void shouldThrowException_WhenQuantityIsZero() {
            assertThatThrownBy(() -> orderValidator.validate("시저샐러드-0"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_QUANTITY.getMessage());
        }

        @Test
        void shouldThrowException_WhenFormatIsInvalidWithNonNumericQuantity() {
            assertThatThrownBy(() -> orderValidator.validate("해산물파스타-한개"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenFormatIsInvalidWithExtraCharacters() {
            assertThatThrownBy(() -> orderValidator.validate("양송이수프:1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenFormatIsInvalidWithNoQuantity() {
            assertThatThrownBy(() -> orderValidator.validate("초코케이크-"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }

        @Test
        void shouldThrowException_WhenFormatIsInvalidWithNoDelimiter() {
            assertThatThrownBy(() -> orderValidator.validate("크리스마스파스타1"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ExceptionMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }
}
