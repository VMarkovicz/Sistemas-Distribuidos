package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class FindRouteReq extends Requisition<FindRouteReq.Payload> {
    @NotNull
    @Valid
    private final FindRouteReq.Payload payload;

    public FindRouteReq(final String token, Long pdi_inicial, Long pdi_final) {
        super(new Header(RequisitionOp.BUSCAR_ROTA, token));
        payload = new FindRouteReq.Payload(pdi_inicial, pdi_final);
    }

    public record Payload(
            @Positive Long pdi_inicial,
            @Positive Long pdi_final
            ) {

    }
}