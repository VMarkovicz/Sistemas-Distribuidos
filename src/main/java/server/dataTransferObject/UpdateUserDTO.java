package server.dataTransferObject;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UpdateUserDTO(Long sender, Long registro, String nome, String email, String senha, Boolean tipo) {
}
