package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.SegmentDTO;

public record AdminUpdateSegmentReply(@NotNull @Valid SegmentDTO payload) implements Reply<SegmentDTO>{
}