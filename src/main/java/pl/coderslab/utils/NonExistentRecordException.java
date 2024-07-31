package pl.coderslab.utils;

public class NonExistentRecordException extends Exception {

    public NonExistentRecordException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public NonExistentRecordException(String errorMessage) {
        super(errorMessage);
    }

    public NonExistentRecordException(Throwable cause) {
        super(cause);
    }

    public NonExistentRecordException() {
        super();
    }
}
