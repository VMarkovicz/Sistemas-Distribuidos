package server.methods;

import protocols.reply.LoginReply;
import protocols.reply.Reply;
import protocols.requisitions.LoginReq;
import server.controller.UserManager;
import server.exception.ServerReplyException;


public class Login extends MethodTemplate{

    public Reply<?> startService(String jsonString) throws ServerReplyException {
        LoginReq loginRequest = buildRequest(jsonString, LoginReq.class);

        String token = UserManager.getInstance().login(loginRequest.getPayload());
        return new LoginReply(token);
    }
}
