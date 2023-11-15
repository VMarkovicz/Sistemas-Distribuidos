package server.methods;

import jwt.JWTManager;
import protocols.reply.DeleteUserReply;
import protocols.reply.Reply;
import protocols.requisitions.DeleteUserReq;
import server.controller.UserManager;
import server.dataTransferObject.DeleteUserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationToken;

public class DeleteUser extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, DeleteUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        var data = requisition.getPayload();
        var user = DeleteUserDTO.builder()
                .sender(JWTManager.getRegistro(requisition.getHeader().token()))
                .registroDelecao(JWTManager.getRegistro(requisition.getHeader().token()))
                .tipo(JWTManager.getTipo(requisition.getHeader().token()))
                .email(data.email())
                .senha(data.senha())
                .build();
        UserManager.getInstance().deleteUserAuthentication(user);
        UserManager.getInstance().deleteUser(user);
        return new DeleteUserReply(JWTManager.getRegistro(requisition.getHeader().token()));
    }
}
