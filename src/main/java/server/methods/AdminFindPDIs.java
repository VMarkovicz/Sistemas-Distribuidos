package server.methods;

import protocols.reply.AdminFindPDIsReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminFindPDIsReq;
import server.controller.PDIManager;
import server.dataTransferObject.PDIDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

import java.util.List;

public class AdminFindPDIs extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminFindPDIsReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        List<PDIDTO> pdiList = PDIManager.getInstance().findPDIs();
        return new AdminFindPDIsReply(pdiList);
    }
}
