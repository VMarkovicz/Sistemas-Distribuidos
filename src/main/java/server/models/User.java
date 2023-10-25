package server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import server.dataTransferObject.CreateUserDTO;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class User {
    @NotNull @Id @GeneratedValue private Long registro;

    @NotNull @Size(min = 3, max = 255) private String nome;

    @NotNull @Email @NaturalId(mutable = true)
    private String email;

    @NotNull private String senha;

    @NotNull private Boolean tipo;

    public User() {
    }

    public static User of(CreateUserDTO user) {
        var entity = new User();
        entity.setEmail(user.email());
        entity.setSenha(user.senha());
        entity.setTipo(user.tipo());
        entity.setNome(user.nome());
        return entity;
    }
}
