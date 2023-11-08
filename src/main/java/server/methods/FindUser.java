package server.methods;

import jwt.JWTManager;
import protocols.reply.FindUserReply;
import protocols.reply.Reply;
import protocols.requisitions.FindUserReq;
import server.controller.UserManager;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationToken;

public class FindUser extends MethodTemplate {
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException{
        var requisition = buildRequest(jsonString, FindUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        var user = UserManager.getInstance().findUser(JWTManager.getRegistro(requisition.getHeader().token()));
        return new FindUserReply(user);
    }
}
