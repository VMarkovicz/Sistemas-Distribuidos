package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record LogoutReply(@NotNull @Valid Payload payload) implements Reply<LogoutReply.Payload>{

    public LogoutReply(){
        this(new Payload("disconnected"));
    }
    public record Payload(String message) {
    }
}
