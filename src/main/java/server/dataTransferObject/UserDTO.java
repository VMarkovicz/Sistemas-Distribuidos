package server.dataTransferObject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.*;
import lombok.Builder;
import server.models.User;
@Builder
public record UserDTO(
        @NotBlank @Size(min = 3, max = 255) String nome,
        @NotBlank @Email String email,

        @NotNull Boolean tipo,
        @Positive
        @SerializedName(value = "registro")
        Long registro
) {
    public static UserDTO of(User userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserDTO(userEntity.getNome(), userEntity.getEmail(), userEntity.getTipo(), userEntity.getRegistro());
    }
}