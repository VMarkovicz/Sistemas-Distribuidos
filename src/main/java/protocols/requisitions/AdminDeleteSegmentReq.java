package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AdminDeleteSegmentReq extends Requisition<AdminDeleteSegmentReq.Payload> {
    @NotNull
    @Valid
    private final AdminDeleteSegmentReq.Payload payload;
    public AdminDeleteSegmentReq(String token, Long pdi_inicial, Long pdi_final) {
        super(new Header(RequisitionOp.DELETAR_SEGMENTO, token));
        payload = new AdminDeleteSegmentReq.Payload(pdi_inicial, pdi_final);
    }

    public record Payload(
            @NotNull
            Long pdi_inicial,
            @NotNull
            Long pdi_final
    ) {
    }
}