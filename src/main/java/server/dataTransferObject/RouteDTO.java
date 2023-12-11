package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Setter;
import server.controller.PDIManager;
import server.controller.SegmentManager;
import server.dataTransferObject.Utils.Direction;
import server.exception.NotFoundException;
import server.models.Segment;

@Builder
public record RouteDTO (
        @NotNull
        String pdi_inicial,
        @NotNull
        String pdi_final,
        @NotNull
        Double distancia,

        String aviso,

        Direction direcao

) {
        public static RouteDTO of(Segment segmentEntity) throws NotFoundException {
                if (segmentEntity == null) {
                        return null;
                }
                var pdi_inicial = PDIManager.getInstance().findPDI(segmentEntity.getPdi_inicial());
                var pdi_final = PDIManager.getInstance().findPDI(segmentEntity.getPdi_final());
                return new RouteDTO(pdi_inicial.nome(), pdi_final.nome(), segmentEntity.getDistancia(), segmentEntity.getAviso(), null);
        }
}