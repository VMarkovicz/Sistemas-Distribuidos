package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jwt.JWTManager;
import lombok.Getter;
import protocols.Optional;

@Getter
public class UpdateUserReq extends Requisition<UpdateUserReq.Payload>{
    @NotNull @Valid
    private final Payload payload;
    public UpdateUserReq(final String token, @Optional String nome, @Optional final String email,
                         @Optional final String senha, @Optional Boolean tipo) {
        super(new Header(RequisitionOp.ATUALIZAR_USUARIO, token));
        payload = new Payload(JWTManager.getRegistro(token), nome, email, senha, tipo);
    }

    public record Payload(
            @Positive Long registro,
            @Size(min = 3, max = 255, message = "Name must contain between 3 e 255 caracters")
            String nome,
            @Email
            String email,
            String senha,
            Boolean tipo
    ) {
    }
}

