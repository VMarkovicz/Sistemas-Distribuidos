package server.dataTransferObject;

import lombok.Builder;

@Builder
public record CreateSegmentDTO(Long pdi_inicial, Long pdi_final, Double distancia, String aviso, Boolean acessivel) {
}
