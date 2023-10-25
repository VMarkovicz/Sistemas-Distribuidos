package server.dataTransferObject;

import jakarta.validation.constraints.*;
import server.models.User;

public record UserDTO(
        @NotBlank @Size(min = 3, max = 255) String nome,
        @NotBlank @Email String email,

        @NotNull Boolean isAdmin,
        @Positive Long id
) {
    public static UserDTO of(User userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new UserDTO(userEntity.getNome(), userEntity.getEmail(), userEntity.getIsAdmin(), userEntity.getId());
    }
}