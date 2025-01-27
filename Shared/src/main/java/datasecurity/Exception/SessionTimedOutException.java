package datasecurity.Exception;

public class SessionTimedOutException extends Exception {


    String message;

    public SessionTimedOutException(String message) {
        super(message);
        this.message = message;
    }
}
