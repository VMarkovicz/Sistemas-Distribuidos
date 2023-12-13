package protocols.reply;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.RouteDTO;

import java.util.List;

public record FindRouteReply(@NotNull @Valid FindRouteReply.Payload payload) implements Reply<FindRouteReply.Payload> {
    public FindRouteReply(List<RouteDTO> routeList) {
        this(new FindRouteReply.Payload(routeList));
    }

    public record Payload(@SerializedName(value = "comandos") List<@NotNull @Valid RouteDTO> routeList) {
    }

}
