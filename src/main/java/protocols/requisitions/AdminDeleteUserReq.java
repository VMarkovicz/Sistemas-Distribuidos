package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AdminDeleteUserReq extends Requisition<AdminDeleteUserReq.Payload> {
    @NotNull @Valid private final Payload payload;

    public AdminDeleteUserReq(String token, Long registro) {
        super(new Header(RequisitionOp.ADMIN_DELETAR_USUARIO, token));
        payload = new Payload(registro);
    }

    public record Payload(
            @Positive
            Long registro
    ) {
    }
}
