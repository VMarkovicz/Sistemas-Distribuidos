package protocols.requisitions;

import lombok.Getter;
import protocols.EmptyPayload;

@Getter
public class AdminFindUsersReq extends Requisition<EmptyPayload> {
    public AdminFindUsersReq(String token){
        super(new Header(RequisitionOp.ADMIN_BUSCAR_USUARIOS, token));
    }
}
