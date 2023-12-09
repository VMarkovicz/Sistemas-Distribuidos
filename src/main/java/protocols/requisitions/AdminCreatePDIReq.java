package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import server.dataTransferObject.Utils.Posicao;
@Getter
public class AdminCreatePDIReq extends Requisition<AdminCreatePDIReq.Payload>{
    @NotNull(message = "Payload cannot be null") @Valid
    private final AdminCreatePDIReq.Payload payload;
    public AdminCreatePDIReq(final String token, final String nome, final Double x, final Double y,
                             final String aviso, final Boolean acessivel) {
        super(new Header(RequisitionOp.CADASTRAR_PDI, token));
        payload = new AdminCreatePDIReq.Payload(nome, new Posicao(x, y), aviso, acessivel);
    }

    public record Payload(
            @NotBlank @Size(min = 3, max = 255) String nome,
            @NotNull Posicao posicao,
            String aviso,
            @NotNull Boolean acessivel
    ) {
    }
}
