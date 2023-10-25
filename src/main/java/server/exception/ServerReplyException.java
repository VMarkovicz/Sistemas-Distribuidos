package server.exception;

import jakarta.validation.constraints.NotNull;
import protocols.reply.ErrorReply;

public abstract class ServerReplyException extends Exception{
    private final int errorCode;

    public ServerReplyException(int errorCode, @NotNull final String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorReply intoResponse() {
        return new ErrorReply(errorCode, getMessage());
    }
}
