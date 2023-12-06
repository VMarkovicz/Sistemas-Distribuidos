package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AdminDeletePDIReq extends Requisition<AdminDeletePDIReq.Payload> {
    @NotNull
    @Valid
    private final AdminDeletePDIReq.Payload payload;
    public AdminDeletePDIReq(String token, Long id) {
        super(new Header(RequisitionOp.DELETAR_PDI, token));
        payload = new AdminDeletePDIReq.Payload(id);
    }

    public record Payload(
            @Positive
            Long id
    ) {
    }
}