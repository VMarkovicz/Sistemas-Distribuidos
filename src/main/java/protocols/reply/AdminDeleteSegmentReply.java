package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AdminDeleteSegmentReply (@NotNull @Valid AdminDeleteSegmentReply.Payload payload) implements Reply<AdminDeleteSegmentReply.Payload> {
    public AdminDeleteSegmentReply(Long id1, Long id2) {
        this(new AdminDeleteSegmentReply.Payload("Segment p1-" + id1 + " p2-" + id2 + " deleted with success."));
    }
    public record Payload(String mensagem) {
    }
}