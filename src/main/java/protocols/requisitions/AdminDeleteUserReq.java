package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jwt.JWTManager;
import lombok.Getter;

import java.util.Objects;

@Getter
public class AdminDeleteUserReq extends Requisition<AdminDeleteUserReq.Payload> {
    @NotNull @Valid private final Payload payload;

    public AdminDeleteUserReq(String token, String registro) {
        super(new Header(RequisitionOp.ADMIN_DELETAR_USUARIO, token));
        Long registerToPayload = Objects.equals(registro, "MY_ID") ? JWTManager.getRegistro(token) : Long.parseLong(registro);

        payload = new Payload(registerToPayload);
    }

    public record Payload(
            @Positive
            Long registro
    ) {
    }
}
