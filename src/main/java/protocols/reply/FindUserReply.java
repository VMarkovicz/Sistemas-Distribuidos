package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.UserDTO;

public record FindUserReply(@NotNull @Valid UserDTO payload) implements Reply<UserDTO>{
}
