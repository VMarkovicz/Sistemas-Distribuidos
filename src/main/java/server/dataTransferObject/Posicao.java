package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;

public record Posicao(
        @NotNull Double x,
        @NotNull Double y
) {
}
