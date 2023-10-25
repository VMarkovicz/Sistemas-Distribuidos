package server.methods;

import protocols.reply.Reply;
import server.exception.ServerReplyException;

public interface Interface {
    <T> T buildRequest(String jsonString, Class<T> clazz) throws ServerReplyException;

    Reply<?> startService(String jsonString) throws ServerReplyException;
}
