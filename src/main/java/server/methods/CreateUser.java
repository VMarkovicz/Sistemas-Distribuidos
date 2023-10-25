package server.methods;

import protocols.reply.CreateUserReply;
import protocols.reply.Reply;
import protocols.requisitions.CreateUserReq;
import server.controller.UserManager;
import server.dataTransferObject.CreateUserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class CreateUser extends MethodTemplate{

    public Reply<?> startService(String jsonString) throws ServerReplyException {

        var requisition = buildRequest(jsonString, CreateUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        var data = requisition.getPayload();
        var user = UserManager.getInstance().createUser(CreateUserDTO.builder().nome(data.nome())
                                                                                .email(data.email())
                                                                                .senha(data.senha())
                                                                                .tipo(data.tipo())
                                                                                .build());

        return new CreateUserReply(user);
    }
}
