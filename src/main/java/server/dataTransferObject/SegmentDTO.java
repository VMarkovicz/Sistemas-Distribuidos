package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import server.models.Segment;

@Builder
public record SegmentDTO (
    @NotNull
    Long pdi_inicial,
    @NotNull
    Long pdi_final,

    Double distancia,

    String aviso,

    @NotNull Boolean acessivel

) {

        public static SegmentDTO of(Segment segmentEntity) {
            if (segmentEntity == null) {
                return null;
            }
            return new SegmentDTO(segmentEntity.getPdi_inicial(), segmentEntity.getPdi_final(), segmentEntity.getDistancia(), segmentEntity.getAviso(), segmentEntity.getAcessivel());
        }
}
