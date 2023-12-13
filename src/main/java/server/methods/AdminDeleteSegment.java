package server.methods;

import jwt.JWTManager;
import protocols.reply.AdminDeleteSegmentReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminDeleteSegmentReq;
import server.controller.SegmentManager;
import server.dataTransferObject.DeleteSegmentDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

public class AdminDeleteSegment extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminDeleteSegmentReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        var data = requisition.getPayload();
        var controller = SegmentManager.getInstance();
        var aux = controller.findSegment(data.pdi_inicial(), data.pdi_final());
        var segment = DeleteSegmentDTO.builder()
                .id(aux.getId())
                .id1(data.pdi_inicial())
                .id2(data.pdi_final())
                .build();

        controller.deleteSegment(segment);
        return new AdminDeleteSegmentReply(data.pdi_inicial(), data.pdi_final());
    }
}
