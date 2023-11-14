package christmas.utils.validator;

import christmas.domain.constants.event.EventConstants;

public class DateValidator extends AbstractValidator<String> {
    @Override
    public void validate(String input) {
        checkBlankOrNull(input);
        checkDateFormat(input);
        checkDateRange(input);
    }

    private void checkBlankOrNull(String input) {
        if (isBlankOrNull(input)) {
            throw new IllegalArgumentException("The date input is blank or null.");
        }
    }

    private void checkDateFormat(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private void checkDateRange(String dateStr) {
        if (!isDateRangeValid(dateStr)) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private boolean isDateRangeValid(String dateStr) {
        int date = Integer.parseInt(dateStr);
        return date >= EventConstants.DECEMBER_FIRST_DAY.getValue() &&
                date <= EventConstants.DECEMBER_LAST_DAY.getValue();
    }
}

