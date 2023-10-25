package server.repository;

import server.exception.ServerReplyException;
import server.models.User;

public interface Repository {
    void create(User instance) throws ServerReplyException;
}
