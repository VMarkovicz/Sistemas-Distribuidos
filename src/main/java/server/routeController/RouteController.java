package server.routeController;


import com.google.gson.JsonSyntaxException;
import gson.Error;
import gson.GoogleJson;
import gson.ValidationGson;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import protocols.reply.Reply;
import protocols.requisitions.EmptyRequisition;
import server.exception.BadReqException;
import server.exception.NotAllowedException;
import server.exception.ServerReplyException;
import server.methods.Interface;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RouteController {
    private Map<String, Interface> routes;

    public static RouteBuild builder() {
        return new RouteBuild();
    }

    public Reply<?> serve(final String request) throws ServerReplyException {

        EmptyRequisition req;
        try {
            req = GoogleJson.decode(request, EmptyRequisition.class);
            ValidationGson.validate(req);
        } catch (JsonSyntaxException e) {
            throw new BadReqException("Invalid header");
        } catch (Error e) {
            throw new BadReqException(e.getMessage());
        }

        var method = routes.get(req.getHeader().operation());
        if (method == null) {
            throw new NotAllowedException(req.getHeader().operation());
        }

        return method.startService(request);
    }

    public static class RouteBuild {
        @NonNull
        private final Map<String, Interface> routes = new HashMap<>();

        public RouteBuild addRoute(String operation, Interface handler) {
            routes.put(operation, handler);
            return this;
        }

        public RouteController build() {
            return new RouteController(routes);
        }
    }
}
