package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeletePDIDTO(@NotNull Long id) {
}
