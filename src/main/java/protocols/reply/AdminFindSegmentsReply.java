package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.SegmentDTO;

import java.util.List;

public record AdminFindSegmentsReply(@NotNull @Valid AdminFindSegmentsReply.Payload payload) implements Reply<AdminFindSegmentsReply.Payload>{
    public AdminFindSegmentsReply(List<SegmentDTO> segmentList){
        this(new AdminFindSegmentsReply.Payload(segmentList));
    }
    public record Payload(@SerializedName(value = "segmentos") List<@NotNull @Valid SegmentDTO> segmentList) {
    }

    public String segmentListFormatted(List<SegmentDTO> list){
        StringBuilder result = new StringBuilder();
        result.append("<html>");
        for (SegmentDTO element : list) {
            result.append("INITIAL_PDI: ").append(element.pdi_inicial()).append(" - ");
            result.append("FINAL_PDI: ").append(element.pdi_final()).append(" - ");
            result.append("Distance: ").append(element.distancia()).append(" - ");
            result.append("Warning: ").append(element.aviso()).append(" - ");
            result.append("Accessible: ").append(element.acessivel()).append("<br>");
        }
        result.append("</html>");
        return result.toString();
    }
}
