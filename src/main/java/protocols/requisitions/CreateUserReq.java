package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserReq extends Requisition<CreateUserReq.Payload>{
    @NotNull(message = "Payload cannot be null") @Valid
    private final CreateUserReq.Payload payload;
    public CreateUserReq(final String token, final String nome, final String email,
                              final String senha) {
        super(new Header(RequisitionOp.CADASTRAR_USUARIO, token));
        payload = new CreateUserReq.Payload(nome, email, senha, false);
    }

    public record Payload(
            @NotBlank(message = "Name is Empty")
            @Size(min = 3, max = 255, message = "Name must contain between 3 e 255 caracters")
            String nome,
            @NotBlank(message = "Email is empty")
            @Email
            String email,
            @NotBlank(message = "Password is empty")
            String senha,
            @NotNull(message = "Type cannot be null")
            Boolean tipo
    ) {
    }
}
