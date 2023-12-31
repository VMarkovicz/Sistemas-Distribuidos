package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ErrorReply(@NotNull @Valid Payload error) implements Reply<ErrorReply.Payload> {
    public ErrorReply(int code, String mensagem) {
        this(new ErrorReply.Payload(code, mensagem));
    }
    @Override
    public Payload payload() {
        return error;
    }
    public record Payload(@Positive int code, @NotBlank String mensagem) {
    }
}