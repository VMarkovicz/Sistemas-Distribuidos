package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.UserDTO;
import server.models.User;

public record CreateUserReply(@NotNull @Valid UserDTO payload) implements Reply<UserDTO>{
    public static CreateUserReply of(User user) {
        return new CreateUserReply(UserDTO.of(user));
    }
}
