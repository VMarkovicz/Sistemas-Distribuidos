package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import server.dataTransferObject.Utils.Direction;

@Builder
public record RouteDTO (
        @NotNull
        String pdi_inicial,
        @NotNull
        String pdi_final,
        @NotNull
        Double distancia,

        String aviso,

        @NotNull Direction direcao

) {

}