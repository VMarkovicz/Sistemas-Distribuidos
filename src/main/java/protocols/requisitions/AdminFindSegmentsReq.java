package protocols.requisitions;

import lombok.Getter;
import protocols.EmptyPayload;
@Getter
public class AdminFindSegmentsReq extends Requisition<EmptyPayload> {
    public AdminFindSegmentsReq(String token){
        super(new Header(RequisitionOp.BUSCAR_SEGMENTOS, token));
    }
}