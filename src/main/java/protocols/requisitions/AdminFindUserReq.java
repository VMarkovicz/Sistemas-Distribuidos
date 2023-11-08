package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AdminFindUserReq extends Requisition<AdminFindUserReq.Payload>{
    @NotNull
    @Valid
    private final Payload payload;

    public AdminFindUserReq(String token, Long registro) {
        super(new Header(RequisitionOp.ADMIN_DELETAR_USUARIO, token));
        payload = new AdminFindUserReq.Payload(registro);
    }
    public record Payload(
            @Positive
            Long registro
    ) {
    }
}
