package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.PDIDTO;

import java.util.List;

public record AdminFindPDIsReply(@NotNull @Valid AdminFindPDIsReply.Payload payload) implements Reply<AdminFindPDIsReply.Payload>{
    public AdminFindPDIsReply(List<PDIDTO> pdiList){
        this(new AdminFindPDIsReply.Payload(pdiList));
    }
    public record Payload(@SerializedName(value = "pdis") List<@NotNull @Valid PDIDTO> pdiList) {
    }
}
