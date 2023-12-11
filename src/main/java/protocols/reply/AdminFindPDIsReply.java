package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.PDIDTO;
import server.dataTransferObject.UserDTO;

import java.util.List;

public record AdminFindPDIsReply(@NotNull @Valid AdminFindPDIsReply.Payload payload) implements Reply<AdminFindPDIsReply.Payload>{
    public AdminFindPDIsReply(List<PDIDTO> pdiList){
        this(new AdminFindPDIsReply.Payload(pdiList));
    }
    public record Payload(@SerializedName(value = "pdis") List<@NotNull @Valid PDIDTO> pdiList) {
    }

    public String pdiListFormatted(List<PDIDTO> list){
        StringBuilder result = new StringBuilder();
        result.append("<html>");
        for (PDIDTO element : list) {
            result.append("ID: ").append(element.id()).append(" - ");
            result.append("Name: ").append(element.nome()).append(" - ");
            result.append("Position:    X=").append(element.posicao().x()).append("  Y=").append(element.posicao().y()).append(" - ");
            result.append("Warning: ").append(element.aviso()).append(" - ");
            result.append("Accessible: ").append(element.acessivel()).append("<br>");
        }
        result.append("</html>");
        return result.toString();
    }
}
