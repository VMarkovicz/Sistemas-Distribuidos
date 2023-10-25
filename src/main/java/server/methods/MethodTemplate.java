package server.methods;

import com.google.gson.JsonSyntaxException;
import gson.Error;
import gson.GoogleJson;
import gson.ValidationGson;
import server.exception.BadReqException;
import server.exception.ServerReplyException;

public abstract class MethodTemplate implements Interface{

    public <T> T buildRequest(String jsonString, Class<T> objectClass) throws ServerReplyException {
        try {
            var request = GoogleJson.decode(jsonString, objectClass);
            ValidationGson.validate(request);
            return request;
        } catch (JsonSyntaxException e) {
            throw new BadReqException("");
        } catch (Error e) {
            throw new BadReqException(e.getMessage());
        }
    }

}
