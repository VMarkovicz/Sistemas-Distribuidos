package server.methods;

import protocols.reply.AdminCreatePDIReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminCreatePDIReq;
import server.controller.PDIManager;
import server.dataTransferObject.CreatePDIDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminCreatePDI extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {

        var requisition = buildRequest(jsonString, AdminCreatePDIReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var pdi = PDIManager.getInstance().createPDI(CreatePDIDTO.builder().nome(data.nome())
                .posicao(data.posicao())
                .aviso(data.aviso())
                .acessivel(data.acessivel())
                .build());

        return new AdminCreatePDIReply(pdi);
    }
}
