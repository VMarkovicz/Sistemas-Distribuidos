package server.exception;

public class NotFoundException extends ServerReplyException {
    public NotFoundException(final String message) {
        super(404, "Unable to find '%s'.".formatted(message));
    }
}
