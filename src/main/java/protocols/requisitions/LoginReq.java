package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginReq extends Requisition<LoginReq.Payload>{

    @NotNull(message = "Null Payload") @Valid private final Payload payload;

    public LoginReq(final String email, final String senha){
        super(new Header(RequisitionOp.LOGIN, null));
        //payload = new Payload("admin@admin", "admin");
        payload = new Payload(email, senha);
    }
    public record Payload(
            @NotBlank(message = "Email field is empty") @Email String email,
            @NotBlank(message = "Password field is empty") String senha) {
    }
}
