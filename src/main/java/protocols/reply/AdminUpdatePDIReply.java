package protocols.reply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import server.dataTransferObject.PDIDTO;

public record AdminUpdatePDIReply(@NotNull @Valid PDIDTO payload) implements Reply<PDIDTO>{
}