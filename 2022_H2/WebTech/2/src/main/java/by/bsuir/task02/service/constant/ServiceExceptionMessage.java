package by.bsuir.task02.service.constant;

public enum ServiceExceptionMessage {
    INVALID_CRITERIA_EXCEPTION_MSG("Criteria is not Valid!"),
    ILLEGAL_ARGUMENT_CRITERIA_EXCEPTION_MSG("No such appliance search criteria exists.");

    private final String message;

    private ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
