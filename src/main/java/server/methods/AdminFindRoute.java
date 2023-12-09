package server.methods;

import protocols.reply.AdminFindRouteReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminFindRouteReq;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

//public class AdminFindRoute extends MethodTemplate{
//
//    public Reply<?> startService(String jsonString) throws ServerReplyException {
//        var requisition = buildRequest(jsonString, AdminFindRouteReq.class);
//        var token = requisition.getHeader().token();
//        ValidationToken.validateToken(token);
//        ValidationAdmin.validateAdmin(token);
//        var data = requisition.getPayload();
//
//        return new AdminFindRouteReply();
//    }
//}