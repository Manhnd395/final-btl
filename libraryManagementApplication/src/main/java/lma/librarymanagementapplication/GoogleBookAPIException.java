package lma.librarymanagementapplication;

public class GoogleBookAPIException extends Exception {
    public GoogleBookAPIException(String message) {
        super(message);
    }

    public GoogleBookAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
