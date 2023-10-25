package server.exception;

public class BadReqException extends ServerReplyException {

    public BadReqException(final String message) {
        super(400, message);
    }

    public BadReqException(int code, final String message) {
        super(code, message);
    }
}
