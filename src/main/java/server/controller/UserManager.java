package server.controller;

import jwt.JWTManager;
import protocols.requisitions.LoginReq;
import server.dataTransferObject.CreateUserDTO;
import server.dataTransferObject.UserDTO;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.exception.UnauthorizedAccessException;
import server.models.User;
import server.repository.UserRepository;

public class UserManager {
    private static UserManager instance = null;
    private final UserRepository repository = new UserRepository();

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String login(LoginReq.Payload login) throws UnauthorizedAccessException {
        var user = repository.login(login.email()).orElseThrow(UnauthorizedAccessException::new);
        if (!user.getSenha().equals(login.senha())) {
            throw new UnauthorizedAccessException();
        }
        return JWTManager.codifyJWT(user.getTipo(), user.getRegistro());
    }

    public UserDTO createUser(CreateUserDTO user) throws ServerReplyException {
        var model = User.of(user);
        repository.create(model);
        return UserDTO.of(model);
    }

}
