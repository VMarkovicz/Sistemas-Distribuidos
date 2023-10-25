package server.exception;

public class UnauthorizedAccessException extends ServerReplyException {
    public UnauthorizedAccessException() {
        super(401, "Unable to authenticate user, invalid token.");
    }
}
