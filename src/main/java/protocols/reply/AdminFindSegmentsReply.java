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
}
