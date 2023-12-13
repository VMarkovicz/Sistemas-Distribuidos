package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminCreateSegmentReq extends Requisition<AdminCreateSegmentReq.Payload>{
    @NotNull(message = "Payload cannot be null") @Valid
    private final AdminCreateSegmentReq.Payload payload;
    public AdminCreateSegmentReq(final String token, final Long pdi_inicial, final Long pdi_final,
                             final String aviso, final Boolean acessivel) {
        super(new Header(RequisitionOp.CADASTRAR_SEGMENTO, token));
        payload = new AdminCreateSegmentReq.Payload(pdi_inicial, pdi_final, aviso, acessivel);
    }

    public record Payload(
            @NotNull Long pdi_inicial,
            @NotNull Long pdi_final,
            String aviso,
            @NotNull Boolean acessivel
    ) {
    }
}
