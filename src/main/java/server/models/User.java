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
import server.dataTransferObject.UpdateUserDTO;

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
    public static User of(UpdateUserDTO user) {
        var entity = new User();
        entity.setRegistro(user.registro());
        entity.setNome(user.nome());
        entity.setEmail(user.email());
        entity.setSenha(user.senha());
        entity.setTipo(user.tipo());
        return entity;
    }
    public void update(User info) {
        if (info.getNome() != null) {
            setNome(info.getNome());
        }
        if (info.getEmail() != null) {
            setEmail(info.getEmail());
        }
        if (info.getSenha() != null) {
            setSenha(info.getSenha());
        }
        if (info.getTipo() != null) {
            setTipo(info.getTipo());
        }
    }
}
