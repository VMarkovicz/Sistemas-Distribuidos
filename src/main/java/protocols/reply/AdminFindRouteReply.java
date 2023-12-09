package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.RouteDTO;

import java.util.List;

public record AdminFindRouteReply(@NotNull @Valid AdminFindRouteReply.Payload payload) implements Reply<AdminFindRouteReply.Payload> {
    public AdminFindRouteReply(List<RouteDTO> routeList) {
        this(new AdminFindRouteReply.Payload(routeList));
    }

    public record Payload(@SerializedName(value = "comandos") List<@NotNull @Valid RouteDTO> routeList) {
    }
}