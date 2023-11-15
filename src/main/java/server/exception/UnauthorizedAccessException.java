package server.exception;

public class UnauthorizedAccessException extends ServerReplyException {
    public UnauthorizedAccessException() {
        super(401, "Unable to authenticate user.");
    }
    public UnauthorizedAccessException(String mensagem) {
        super(401, mensagem);
    }
}
