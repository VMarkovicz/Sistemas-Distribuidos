package server.dataTransferObject;

import lombok.Builder;

@Builder
public record UpdateSegmentDTO (Long id, Long pdi_inicial, Long pdi_final, String aviso, Boolean acessivel){
}
