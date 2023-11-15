package server.methods;

import jwt.JWTManager;
import protocols.reply.AdminUpdateUserReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminUpdateUserReq;
import server.controller.UserManager;
import server.dataTransferObject.UpdateUserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminUpdateUser extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminUpdateUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var user = UserManager.getInstance().updateUser(UpdateUserDTO.builder()
                .sender(JWTManager.getRegistro(token))
                .registro(data.registro())
                .nome(data.nome())
                .email(data.email())
                .senha(data.senha())
                .tipo(data.tipo())
                .build());
        return new AdminUpdateUserReply(user);
    }
}
