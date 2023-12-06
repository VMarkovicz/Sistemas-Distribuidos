package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import protocols.Optional;
@Getter
public class AdminUpdateSegmentReq extends Requisition<AdminUpdateSegmentReq.Payload>{
    @NotNull
    @Valid
    private final AdminUpdateSegmentReq.Payload payload;
    public AdminUpdateSegmentReq(final String token, Long pdi_inicial, Long pdi_final,
                             @Optional final String aviso, @Optional Boolean acessivel) {
        super(new Header(RequisitionOp.ATUALIZAR_SEGMENTO, token));
        payload = new AdminUpdateSegmentReq.Payload(pdi_inicial, pdi_final, aviso, acessivel);
    }

    public record Payload(
            @Positive Long pdi_inicial,
            @Positive Long pdi_final,
            String aviso,
            Boolean acessivel
    ) {
    }
}