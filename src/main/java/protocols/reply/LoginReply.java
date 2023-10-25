package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginReply(@NotNull @Valid Payload payload) implements Reply<LoginReply.Payload> {

    public LoginReply(final String token){
        this(new Payload(token));
    }

    public record Payload(@NotBlank String token) {
    }
}
