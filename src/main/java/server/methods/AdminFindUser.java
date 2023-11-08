package server.methods;

import protocols.reply.FindUserReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminFindUserReq;
import server.controller.UserManager;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminFindUser extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminFindUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var user = UserManager.getInstance().findUser(data.registro());
        return new FindUserReply(user);
    }
}
