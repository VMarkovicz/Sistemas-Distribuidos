package server.dataTransferObject;

import lombok.Builder;

@Builder
public record UpdateUserDTO(Long registro, String nome, String email, String senha, Boolean tipo) {
}
