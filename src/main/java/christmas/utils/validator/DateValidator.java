package christmas.utils.validator;

import christmas.domain.constants.event.EventConstants;
import christmas.exception.ExceptionMessage;
import christmas.utils.Parser;

public class DateValidator extends AbstractValidator<String> {

    @Override
    public void validate(String input) {
        validateInput(input);
        int date = parseDate(input);
        checkDateRange(date);
    }

    private void validateInput(String input) {
        if (isBlankOrNull(input)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DATE_INPUT.getMessage());
        }
    }

    private int parseDate(String input) {
        return Parser.parseInteger(input, ExceptionMessage.INVALID_DATE.getMessage());
    }

    private void checkDateRange(int date) {
        if (date < EventConstants.DECEMBER_FIRST_DAY.getValue() ||
                date > EventConstants.DECEMBER_LAST_DAY.getValue()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DATE.getMessage());
        }
    }
}

