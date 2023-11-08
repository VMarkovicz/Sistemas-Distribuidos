package protocols.requisitions;

import lombok.Getter;
import protocols.EmptyPayload;

@Getter
public class FindUserReq extends Requisition<EmptyPayload>{
    public FindUserReq(String token){
        super(new Header(RequisitionOp.BUSCAR_USUARIO, token));
    }
}
