package server.methods;

import lombok.Getter;
import protocols.reply.AdminCreateSegmentReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminCreateSegmentReq;
import server.controller.PDIManager;
import server.controller.SegmentManager;
import server.dataTransferObject.CreateSegmentDTO;
import server.dataTransferObject.PDIDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;
@Getter
public class AdminCreateSegment extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {

        var requisition = buildRequest(jsonString, AdminCreateSegmentReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        PDIManager controller = PDIManager.getInstance();
        PDIDTO pdi_inicial = controller.findPDI(data.pdi_inicial());
        PDIDTO pdi_final = controller.findPDI(data.pdi_final());

        var segment = SegmentManager.getInstance().createSegment(CreateSegmentDTO.builder()
                .pdi_inicial(data.pdi_inicial())
                .pdi_final(data.pdi_final())
                .distancia(Distance.calculateDistance(pdi_inicial.posicao().x(), pdi_inicial.posicao().y(), pdi_final.posicao().x(), pdi_final.posicao().y()))
                .aviso(data.aviso())
                .acessivel(data.acessivel())
                .build());

        return new AdminCreateSegmentReply(segment);
    }
}
