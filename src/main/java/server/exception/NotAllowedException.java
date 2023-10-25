package server.exception;

public class NotAllowedException extends ServerReplyException {
    public NotAllowedException(final String method) {
        super(405, "%s not recognized".formatted(method));
    }
}
