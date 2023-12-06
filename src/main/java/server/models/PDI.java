package server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import server.dataTransferObject.CreatePDIDTO;
import server.dataTransferObject.UpdatePDIDTO;

@Entity
@Table(name = "pdi")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PDI {
    @NotNull
    @Id
    @GeneratedValue
    private Long id;
    @NotNull @Size(min = 3, max = 255)
    private String nome;
    @NotNull
    private Double X;
    @NotNull
    private Double Y;

    private String aviso;
    @NotNull
    private Boolean acessivel;

    public PDI() {

    }

    public static PDI of(CreatePDIDTO pdi) {
        var entity = new PDI();
        entity.setNome(pdi.nome());
        entity.setX(pdi.posicao().x());
        entity.setY(pdi.posicao().y());
        entity.setAviso(pdi.aviso());
        entity.setAcessivel(pdi.acessivel());
        return entity;
    }
    public static PDI of(UpdatePDIDTO pdi) {
        var entity = new PDI();
        entity.setId(pdi.id());
        entity.setNome(pdi.nome());
        entity.setAviso(pdi.aviso());
        entity.setAcessivel(pdi.acessivel());
        return entity;
    }
    public void updatePDI(PDI info) {
        if (info.getNome() != null) {
            setNome(info.getNome());
        }
        if (info.getAviso() != null) {
            setAviso(info.getAviso());
        }
        if (info.getAcessivel() != null) {
            setAcessivel(info.getAcessivel());
        }
    }
}
