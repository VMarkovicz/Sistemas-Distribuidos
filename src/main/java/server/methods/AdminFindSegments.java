package server.methods;

import protocols.reply.AdminFindSegmentsReply;
import protocols.reply.Reply;
import protocols.requisitions.AdminFindSegmentsReq;
import server.controller.SegmentManager;
import server.dataTransferObject.SegmentDTO;
import server.exception.ServerReplyException;
import server.methods.validation.ValidationAdmin;
import server.methods.validation.ValidationToken;

import java.util.List;

public class AdminFindSegments extends MethodTemplate{
    @Override
    public Reply<?> startService(String jsonString) throws ServerReplyException {
        var requisition = buildRequest(jsonString, AdminFindSegmentsReq.class);
        var token = requisition.getHeader().token();
        ValidationToken.validateToken(token);
        ValidationAdmin.validateAdmin(token);
        List<SegmentDTO> segmentList = SegmentManager.getInstance().findSegments();
        return new AdminFindSegmentsReply(segmentList);
    }
}
