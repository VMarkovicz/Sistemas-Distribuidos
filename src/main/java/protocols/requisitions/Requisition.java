package protocols.requisitions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public abstract class Requisition<T> {
    @NotNull(message = "Header is null") @Valid final Header header;

    public T getPayload() {
        return null;
    }
}
