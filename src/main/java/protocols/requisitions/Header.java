package protocols.requisitions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Header(
        @NotBlank(message = "operation is empty") String operation,
        @Pattern(regexp = "(?:\\w|-){2,}(?:\\.(?:\\w|-){2,}){2}", message = "Invalid JWT")
        String token) {
}
