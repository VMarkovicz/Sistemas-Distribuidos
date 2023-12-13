package protocols.requisitions;

import lombok.Getter;
import protocols.EmptyPayload;

@Getter
public class AdminFindPDIsReq extends Requisition<EmptyPayload> {
    public AdminFindPDIsReq(String token){
        super(new Header(RequisitionOp.BUSCAR_PDIS, token));
    }
}