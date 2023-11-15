package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DeleteUserReply (@NotNull @Valid DeleteUserReply.Payload payload) implements Reply<DeleteUserReply.Payload> {
    public DeleteUserReply(Long registro) {
        this(new Payload("User deleted with sucess: " + registro));
    }
    public record Payload(String mensagem) {
    }
}
