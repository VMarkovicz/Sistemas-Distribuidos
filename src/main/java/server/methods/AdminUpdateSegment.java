package server.methods;

import protocols.reply.AdminUpdateSegmentReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminUpdateSegmentReq;
import server.controller.SegmentManager;
import server.dataTransferObject.UpdateSegmentDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminUpdateSegment extends MethodTemplate{
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminUpdateSegmentReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var controller = SegmentManager.getInstance();
        var aux = controller.findSegment(data.pdi_inicial(), data.pdi_final());
        var segment = SegmentManager.getInstance().updateSegment(UpdateSegmentDTO.builder()
                .id(aux.getId())
                .pdi_inicial(data.pdi_inicial())
                .pdi_final(data.pdi_final())
                .aviso(data.aviso())
                .acessivel(data.acessivel())
                .build());
        return new AdminUpdateSegmentReply(segment);
    }
}
