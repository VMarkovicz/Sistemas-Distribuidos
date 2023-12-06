package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.PDIDTO;

public record AdminCreatePDIReply(@NotNull @Valid PDIDTO payload) implements Reply<PDIDTO>{
}