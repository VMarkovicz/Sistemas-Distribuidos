package server.dataTransferObject;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import server.models.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class NodeDTO {
    @NotNull
    private final Long pdiInicial;

    @NotNull
    private final Long pdiFinal;

    private final Double distancia;

    private final String aviso;

    private  NodeDTO father;

    private  List<NodeDTO> neighbors;

    public NodeDTO(
            @NotNull Long pdiInicial,
            @NotNull Long pdiFinal,
            Double distancia,
            String aviso,
            NodeDTO father,
            List<NodeDTO> neighbors
    ) {
        this.pdiInicial = pdiInicial;
        this.pdiFinal = pdiFinal;
        this.distancia = distancia;
        this.aviso = aviso;
        this.father = father;
        this.neighbors = neighbors;
    }

    public static NodeDTO of(Segment segment) {
        if (segment == null) {
            return null;
        }
        return new NodeDTO(segment.getPdi_inicial(), segment.getPdi_final(), segment.getDistancia(), segment.getAviso(), null, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "NodeDTO{" +
                "pdiInicial=" + pdiInicial +
                ", pdiFinal=" + pdiFinal +
                ", distancia=" + distancia +
                ", aviso='" + aviso + '\'' +
                '}';
    }
}
