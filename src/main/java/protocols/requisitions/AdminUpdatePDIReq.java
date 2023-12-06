package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import protocols.Optional;

@Getter
public class AdminUpdatePDIReq extends Requisition<AdminUpdatePDIReq.Payload>{
    @NotNull
    @Valid
    private final AdminUpdatePDIReq.Payload payload;
    public AdminUpdatePDIReq(final String token, Long id, @Optional String nome,
                              @Optional final String aviso, @Optional Boolean acessivel) {
        super(new Header(RequisitionOp.ATUALIZAR_PDI, token));
        payload = new AdminUpdatePDIReq.Payload(id, nome, aviso, acessivel);
    }

    public record Payload(
            @Positive Long id,
            @Size(min = 3, max = 255, message = "Name must contain between 3 e 255 caracters")
            String nome,
            String aviso,
            Boolean acessivel
    ) {
    }
}
