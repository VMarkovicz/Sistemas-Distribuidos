package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeleteSegmentDTO (@NotNull Long id, @NotNull Long id1, @NotNull Long id2) {
}
