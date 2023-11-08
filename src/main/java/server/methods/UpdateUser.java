package server.methods;

import protocols.reply.Reply;
import protocols.reply.UpdateUserReply;
import protocols.requisitions.UpdateUserReq;
import server.controller.UserManager;
import server.dataTransferObject.UpdateUserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationToken;

public class UpdateUser extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, UpdateUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        var data = requisition.getPayload();
        var user = UserManager.getInstance().updateUser(UpdateUserDTO.builder()
                                                                        .registro(data.registro())
                                                                        .nome(data.nome())
                                                                        .email(data.email())
                                                                        .senha(data.senha())
                                                                        .tipo(data.tipo())
                                                                        .build());
        return new UpdateUserReply(user);
    }
}

