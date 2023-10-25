package server.dataTransferObject;

import lombok.Builder;

@Builder
public record CreateUserDTO(String nome, String email, String senha, Boolean tipo) {
}
