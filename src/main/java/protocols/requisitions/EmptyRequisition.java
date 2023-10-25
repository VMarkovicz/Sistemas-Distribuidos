package protocols.requisitions;

import protocols.EmptyPayload;

public class EmptyRequisition extends Requisition<EmptyPayload>{
    public EmptyRequisition(final Header header) {
        super(header);
    }
}
