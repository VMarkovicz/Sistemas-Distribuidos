package server.dataTransferObject;

import jakarta.validation.constraints.*;
import lombok.Builder;
import server.models.PDI;


@Builder
public record PDIDTO(
        @Positive
        Long id,
        @NotBlank @Size(min = 3, max = 255) String nome,

        @NotNull Posicao posicao,
        String aviso,

        @NotNull Boolean acessivel

) {

    public static PDIDTO of(PDI pdiEntity) {
        if (pdiEntity == null) {
            return null;
        }
        return new PDIDTO(pdiEntity.getId(), pdiEntity.getNome(), new Posicao(pdiEntity.getX(), pdiEntity.getY()), pdiEntity.getAviso(), pdiEntity.getAcessivel());
    }
}
