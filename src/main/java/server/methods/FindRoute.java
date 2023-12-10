package server.methods;

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