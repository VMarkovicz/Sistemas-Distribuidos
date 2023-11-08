package server.methods;

import protocols.reply.AdminFindUsersReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminFindUsersReq;
import server.controller.UserManager;
import server.dataTransferObject.UserDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

import java.util.List;

public class AdminFindUsers extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException{
        var requisition = buildRequest(jsonString, AdminFindUsersReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        List<UserDTO> userList = UserManager.getInstance().findUsers();
        return new AdminFindUsersReply(userList);
    }
}
