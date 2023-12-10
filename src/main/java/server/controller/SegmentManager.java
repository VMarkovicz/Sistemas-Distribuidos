package server.controller;

import server.dataTransferObject.*;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.Segment;
import server.repository.SegmentRepository;

import java.util.List;

public class SegmentManager {
    private static SegmentManager instance = null;
    private final SegmentRepository repository = new SegmentRepository();

    public static SegmentManager getInstance() {
        if (instance == null) {
            instance = new SegmentManager();
        }
        return instance;
    }

    public SegmentDTO createSegment(CreateSegmentDTO segment) throws ServerReplyException {
        var model = Segment.of(segment);
        repository.createSegment(model);
        return SegmentDTO.of(model);
    }

    public void deleteSegment(DeleteSegmentDTO segment) throws ServerReplyException{
        repository.deleteSegmentById(segment.id1(), segment.id2());
    }

    public void deleteAssociateSegments(Long segmentID){
        repository.findAssociateSegments(segmentID);
    }

    public Segment findSegment(Long pdi_inicial, Long pdi_final) throws NotFoundException {
        var entity = repository.findSegment(pdi_inicial, pdi_final)
                .orElseThrow(() -> new NotFoundException("Segment not found"));
        return entity;
    }

    public List<Segment> findAccessibleSegments() throws NotFoundException {
        return repository.findAccessibleSegments()
                .stream()
                .toList();
    }

    public List<SegmentDTO> findSegments() throws ServerReplyException {
        return repository.findAllSegments()
                .stream()
                .map(SegmentDTO::of)
                .toList();
    }
    public SegmentDTO updateSegment(UpdateSegmentDTO segment) throws ServerReplyException {
        var entity = repository.updateSegment(segment.id(), Segment.of(segment));
        return SegmentDTO.of(entity);
    }
}
