package server.repository;

import server.dataTransferObject.UserDTO;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.User;

import java.util.List;
import java.util.Optional;

public interface Repository {
    void create(User instance) throws ServerReplyException;
    Optional<User> find(Long registro);
    List<User> findAllUsers();
    void deleteById(Long registro) throws NotFoundException;
    User update(Long registro, User instance) throws ServerReplyException;
}

