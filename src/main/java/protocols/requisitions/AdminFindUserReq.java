package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jwt.JWTManager;
import lombok.Getter;
import protocols.Optional;

@Getter
public class AdminFindUserReq extends Requisition<AdminFindUserReq.Payload>{
    @NotNull
    @Valid
    private final AdminFindUserReq.Payload payload;

    public AdminFindUserReq(String token, @Optional Long registro) {
        super(new Header(RequisitionOp.ADMIN_BUSCAR_USUARIO, token));
        if(registro == null){
            registro = JWTManager.getRegistro(token);
        }
        payload = new AdminFindUserReq.Payload(registro);
    }
    public record Payload(
            @Positive
            Long registro
    ) {
    }
}
