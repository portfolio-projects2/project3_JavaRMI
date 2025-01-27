package datasecurity.Exception;

public class UnauthorizedException extends Exception{
    String message;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }
}
