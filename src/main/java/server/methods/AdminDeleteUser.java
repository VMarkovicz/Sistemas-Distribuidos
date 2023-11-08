package server.methods;

import jwt.JWTManager;
import protocols.reply.AdminDeleteUserReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminDeleteUserReq;
import server.controller.UserManager;
import server.dataTransferObject.DeleteUserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminDeleteUser extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminDeleteUserReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var user = DeleteUserDTO.builder()
                        .sender(JWTManager.getRegistro(requisition.getHeader().token()))
                        .registroDelecao(data.registro())
                        .tipo(JWTManager.getTipo(requisition.getHeader().token()))
                        .build();
        UserManager.getInstance().deleteUser(user);
        return new AdminDeleteUserReply();
    }
}
