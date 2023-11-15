package server.exception;

public class ForbiddenAccessException extends ServerReplyException {

    public ForbiddenAccessException() {
        super(403, "User does not have enough permission to access this resource.");
    }

    public ForbiddenAccessException(final String mensagem) {
        super(403, "User '%s' does not have enough permission to access this resource.".formatted(mensagem));
    }
}
