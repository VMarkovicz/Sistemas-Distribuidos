package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AdminFindRouteReq extends Requisition<AdminFindRouteReq.Payload> {
    @NotNull
    @Valid
    private final AdminFindRouteReq.Payload payload;

    public AdminFindRouteReq(final String token, Long pdi_inicial, Long pdi_final) {
        super(new Header(RequisitionOp.BUSCAR_ROTA, token));
        payload = new AdminFindRouteReq.Payload(pdi_inicial, pdi_final);
    }

    public record Payload(
            @Positive Long pdi_inicial,
            @Positive Long pdi_final
            ) {

    }
}