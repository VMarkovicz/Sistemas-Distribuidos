package server.exception;

public class BadReqException extends ServerReplyException {

    public BadReqException(final String mensagem) {
        super(400, mensagem);
    }

    public BadReqException(int code, final String mensagem) {
        super(code, mensagem);
    }
}
