package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.UserDTO;
import server.models.User;

public record CreateUserReply(@NotNull @Valid UserDTO payload) implements Reply<UserDTO>{
}
