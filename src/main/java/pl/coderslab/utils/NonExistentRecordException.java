package pl.coderslab.utils;

public class NonExistentRecord extends Exception {

    public NonExistentRecord(String errorMessage, Throwable cause) {
        super("Specified record does not exist: " + errorMessage, cause);
    }

    public NonExistentRecord(String errorMessage) {
        super("Specified record does not exist: " + errorMessage);
    }

    public NonExistentRecord(Throwable cause) {
        super(cause);
    }

    public NonExistentRecord() {
        super();
    }
}
