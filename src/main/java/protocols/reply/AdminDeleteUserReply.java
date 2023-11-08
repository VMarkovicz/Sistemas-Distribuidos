package protocols.reply;

import protocols.EmptyPayload;

public record AdminDeleteUserReply (EmptyPayload payload) implements Reply<EmptyPayload> {
    public AdminDeleteUserReply() {
        this(new EmptyPayload());
    }
}