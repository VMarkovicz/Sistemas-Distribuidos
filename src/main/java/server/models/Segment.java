package server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import server.dataTransferObject.CreateSegmentDTO;
import server.dataTransferObject.NodeDTO;
import server.dataTransferObject.UpdateSegmentDTO;

@Entity
@Table(name = "segments", uniqueConstraints = {         @UniqueConstraint(columnNames = {"nome_inicio", "nome_final"})})
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Segment {
    @NotNull
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long pdi_inicial;
    @NotNull
    private Long pdi_final;
    @NotNull
    private Double distancia;

    private String aviso;
    @NotNull
    private Boolean acessivel;


    public Segment() {

    }

    public static Segment of(CreateSegmentDTO segment) {
        var entity = new Segment();
        entity.setPdi_inicial(segment.pdi_inicial());
        entity.setPdi_final(segment.pdi_final());
        entity.setDistancia(segment.distancia());
        entity.setAviso(segment.aviso());
        entity.setAcessivel(segment.acessivel());
        return entity;
    }
    public static Segment of(UpdateSegmentDTO segment) {
        var entity = new Segment();
        entity.setPdi_inicial(segment.pdi_inicial());
        entity.setPdi_final(segment.pdi_final());
        entity.setAviso(segment.aviso());
        entity.setAcessivel(segment.acessivel());
        return entity;
    }
    public void updateSegment(Segment info) {
        if (info.getAviso() != null) {
            setAviso(info.getAviso());
        }
        if (info.getAcessivel() != null) {
            setAcessivel(info.getAcessivel());
        }
    }
}
