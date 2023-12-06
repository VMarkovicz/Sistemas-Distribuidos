package server.methods;

import jwt.JWTManager;
import protocols.reply.AdminDeletePDIReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminDeletePDIReq;
import server.controller.PDIManager;
import server.controller.SegmentManager;
import server.dataTransferObject.DeletePDIDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminDeletePDI extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminDeletePDIReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var pdi = DeletePDIDTO.builder()
                .id(data.id())
                .build();
        var controller = PDIManager.getInstance();
        controller.findPDI(data.id());
        controller.deletePDI(pdi);
        var auxController = SegmentManager.getInstance();
        auxController.deleteAssociateSegments(data.id());
        return new AdminDeletePDIReply(data.id());
    }
}
