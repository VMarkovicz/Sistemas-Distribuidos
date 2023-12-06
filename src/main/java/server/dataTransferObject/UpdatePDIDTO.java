package server.dataTransferObject;

import lombok.Builder;

@Builder
public record UpdatePDIDTO(Long id, String nome, String aviso, Boolean acessivel) {
}
