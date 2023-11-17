package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.UserDTO;
import java.util.List;

public record AdminFindUsersReply(@NotNull @Valid Payload payload) implements Reply<AdminFindUsersReply.Payload>{
    public AdminFindUsersReply(List<UserDTO> userList){
        this(new Payload(userList));
    }
    public record Payload(@SerializedName(value = "usuarios") List<@NotNull @Valid UserDTO> userList) {
    }

    public String userListFormatted(List<UserDTO> list){
        StringBuilder result = new StringBuilder();

        for (UserDTO element : list) {
            result.append("Name: ").append(element.nome()).append(",");
            result.append("Email: ").append(element.email()).append(",");
            result.append("Type: ").append(element.tipo()).append(",");
            result.append("ID: ").append(element.registro()).append(",");
            result.append("\n");
        }


        return result.toString();
    }
}
