package protocols.reply;

import protocols.EmptyPayload;

public record DeleteUserReply (EmptyPayload payload) implements Reply<EmptyPayload> {
    public DeleteUserReply() {
        this(new EmptyPayload());
    }
}