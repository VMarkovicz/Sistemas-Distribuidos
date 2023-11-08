package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.UserDTO;

public record UpdateUserReply(@NotNull @Valid UserDTO payload) implements Reply<UserDTO>{
}
