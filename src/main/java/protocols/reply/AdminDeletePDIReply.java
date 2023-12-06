package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AdminDeletePDIReply (@NotNull @Valid AdminDeletePDIReply.Payload payload) implements Reply<AdminDeletePDIReply.Payload> {
    public AdminDeletePDIReply(Long id) {
        this(new AdminDeletePDIReply.Payload("PDI deleted with success: " + id));
    }
    public record Payload(String mensagem) {
    }
}