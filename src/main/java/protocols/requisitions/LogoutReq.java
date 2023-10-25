package protocols.requisitions;

import protocols.EmptyPayload;

public class LogoutReq extends Requisition<EmptyPayload> {
    public LogoutReq(final String token){
        super(new Header(RequisitionOp.LOGOUT, token));
    }
}
