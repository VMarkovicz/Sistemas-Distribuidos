package server.repository;

import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import server.dataTransferObject.NodeDTO;
import server.exception.BadReqException;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.Segment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SegmentRepository {
    private final SessionFactory sessionFactory;

    public SegmentRepository(){
        this.sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("server.models");
    }

    public void createSegment(Segment instance) throws ServerReplyException {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(instance);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadReqException("This segment already exists");
            }
        }
    }

    public void deleteSegmentById(Long id1, Long id2) {
        sessionFactory.inTransaction(session -> {
            Optional<Segment> segment = findSegment(id1, id2);
            session.createMutationQuery("delete from Segment where id = :id")
                    .setParameter("id", segment.get().getId())
                    .executeUpdate();
        });
    }

    public void findAssociateSegments(Long segmentID){
        try (Session session = sessionFactory.openSession()) {
            List<Segment> segments = session.createQuery("from Segment where pdi_inicial = :id or pdi_final = :id", Segment.class)
                    .setParameter("id", segmentID)
                    .getResultList();
            deleteAssociateSegments(segments);
        }
    }
    public void deleteAssociateSegments(List<Segment> segments) {
        sessionFactory.inTransaction(session -> {
            for (Segment segment : segments) {
                System.out.println(segment.getId());
                session.createMutationQuery("delete from Segment where id = :batata")
                        .setParameter("batata", segment.getId())
                        .executeUpdate();
            }
        });
    }
    public Optional<Segment> findSegment(Long id1, Long id2) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Segment where pdi_inicial = :id1 and pdi_final = :id2", Segment.class)
                    .setParameter("id1", id1)
                    .setParameter("id2", id2)
                    .uniqueResultOptional();
        }
    }

    public List<Segment> findAllSegments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Segment ", Segment.class).getResultList();
        }
    }

    public List<Segment> findAccessibleSegments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Segment where acessivel = true", Segment.class)
                    .getResultList();
        }
    }

    public Segment updateSegment(Long id, Segment instance) throws ServerReplyException {
        try (Session session = sessionFactory.openSession()) {

            Segment segment = session.byId(Segment.class)
                    .loadOptional(id)
                    .orElseThrow(() -> new NotFoundException("Segment with id " + id + " does not exist."));

            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                segment.updateSegment(instance);
                session.merge(segment);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadReqException("Segment with PDIs " + segment.getPdi_inicial() + " and " + segment.getPdi_final() + " already exists");
            }
            return segment;
        }
    }

    public List<Segment> findNeighbors(NodeDTO currentNode) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Segment where pdi_inicial = :pdi_final and acessivel = true", Segment.class)
                    .setParameter("pdi_final", currentNode.getPdiFinal())
                    .getResultList();
        }
    }

}
