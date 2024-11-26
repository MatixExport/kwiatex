package indie.outsource.repositories.redis;

public class JsonException extends RuntimeException {
    public JsonException(String message) {
        super(message);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}
