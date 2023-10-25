package server.methods;

import protocols.reply.LogoutReply;
import protocols.reply.Reply;
import protocols.requisitions.LogoutReq;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationToken;

public class Logout extends MethodTemplate{

    public Reply<?> startService(String jsonString) throws ServerReplyException {

        var requisition = buildRequest(jsonString, LogoutReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);

        return new LogoutReply();
    }
}
