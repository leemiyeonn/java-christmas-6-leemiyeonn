package christmas.utils.validator;

public abstract class AbstractValidator<T> {
    public abstract void validate(T t);

    protected boolean isBlankOrNull(String input) {
        return input == null || input.isBlank();
    }
}
