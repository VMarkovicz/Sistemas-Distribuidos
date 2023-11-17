package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record AdminDeleteUserReply (@NotNull @Valid Payload payload) implements Reply<AdminDeleteUserReply.Payload> {
    public AdminDeleteUserReply(Long registro) {
        this(new AdminDeleteUserReply.Payload("User deleted with success: " + registro));
    }
    public record Payload(String mensagem) {
    }
}