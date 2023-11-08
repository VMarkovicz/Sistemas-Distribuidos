package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import protocols.Optional;
@Getter
public class AdminUpdateUserReq extends Requisition<AdminUpdateUserReq.Payload>{
    @NotNull
    @Valid
    private final AdminUpdateUserReq.Payload payload;
    public AdminUpdateUserReq(final String token, Long registro, @Optional String nome, @Optional final String email,
                         @Optional final String senha, @Optional Boolean tipo) {
        super(new Header(RequisitionOp.ADMIN_ATUALIZAR_USUARIO, token));
        payload = new AdminUpdateUserReq.Payload(registro, nome, email, senha, tipo);
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
