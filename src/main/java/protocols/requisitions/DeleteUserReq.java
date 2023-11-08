package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteUserReq extends Requisition<DeleteUserReq.Payload> {
    @NotNull
    @Valid
    private final Payload payload;

    public DeleteUserReq(String token, String email, String senha) {
        super(new Header(RequisitionOp.DELETAR_USUARIO, token));
        payload = new Payload(email, senha);
    }

    public record Payload(
            @NotBlank(message = "Email is empty")
            @Email String email,
            @NotBlank(message = "Password is empty")
            String senha
    ) {
    }
}
