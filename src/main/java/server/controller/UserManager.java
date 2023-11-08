package server.controller;

import jwt.JWTManager;
import protocols.requisitions.LoginReq;
import server.dataTransferObject.CreateUserDTO;
import server.dataTransferObject.DeleteUserDTO;
import server.dataTransferObject.UpdateUserDTO;
import server.dataTransferObject.UserDTO;
import server.exception.BadReqException;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.exception.UnauthorizedAccessException;
import server.models.User;
import server.repository.UserRepository;

import java.util.List;

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

    public void deleteUser(DeleteUserDTO user) throws ServerReplyException{
        if (user.tipo() && user.sender().equals(user.registroDelecao())) {
            if (!repository.tryDelete(user.registroDelecao())) {
                throw new BadReqException("bomdia");
            }
        } else {
            repository.deleteById(user.registroDelecao());
        }
    }
    public UserDTO findUser(long id) throws NotFoundException {
        var entity = repository.find(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserDTO.of(entity);
    }
    public List<UserDTO> findUsers() {
        return repository.findAllUsers()
                .stream()
                .map(UserDTO::of)
                .toList();
    }
    public UserDTO updateUser(UpdateUserDTO user) throws ServerReplyException {
        var entity = repository.update(user.registro(), User.of(user));
        return UserDTO.of(entity);
    }
}
