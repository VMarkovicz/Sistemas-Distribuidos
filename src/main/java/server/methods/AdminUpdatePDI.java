package server.methods;

import protocols.reply.AdminUpdatePDIReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminUpdatePDIReq;
import server.controller.PDIManager;
import server.dataTransferObject.UpdatePDIDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminUpdatePDI extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminUpdatePDIReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var pdi = PDIManager.getInstance().updatePDI(UpdatePDIDTO.builder()
                .id(data.id())
                .nome(data.nome())
                .aviso(data.aviso())
                .acessivel(data.acessivel())
                .build());
        return new AdminUpdatePDIReply(pdi);
    }
}
