package server.methods;

import protocols.reply.FindRouteReply;
import protocols.reply.Reply;
import protocols.requisitions.FindRouteReq;
import server.controller.RouteManager;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class FindRoute extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, FindRouteReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        var data = requisition.getPayload();
        var routes = RouteManager.getInstance().calculateRoute(data.pdi_inicial(), data.pdi_final());
        return new FindRouteReply(routes);
    }
}