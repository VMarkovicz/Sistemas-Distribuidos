package server.dataTransferObject;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record DeleteUserDTO(@NonNull Long sender, @NonNull Boolean tipo,
                          @NonNull Long registroDelecao,
                          String email, String senha) {
}
