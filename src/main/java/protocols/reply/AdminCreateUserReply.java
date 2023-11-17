package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import server.dataTransferObject.UserDTO;
import server.models.User;

public record AdminCreateUserReply(@NotNull @Valid UserDTO payload) implements Reply<UserDTO>{
}
