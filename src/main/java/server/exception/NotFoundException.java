package server.exception;

public class NotFoundException extends ServerReplyException {
    public NotFoundException(final String mensagem) {
        super(404, "Unable to find '%s'.".formatted(mensagem));
    }
}
