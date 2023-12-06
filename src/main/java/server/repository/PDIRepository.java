package server.repository;

import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import server.exception.BadReqException;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.PDI;

import java.util.List;
import java.util.Optional;

public class PDIRepository {
    private final SessionFactory sessionFactory;

    public PDIRepository(){
        this.sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("server.models");
    }

    public void createPDI(PDI instance) throws ServerReplyException {
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
                throw new BadReqException("This PDI already exists");
            }
        }
    }

    public void deletePDI(Long id) {
        sessionFactory.inTransaction(session -> {
            PDI pdi = session.find(PDI.class, id);
            session.createMutationQuery("delete from PDI where id = :id")
                    .setParameter("id", pdi.getId())
                    .executeUpdate();
        });
    }

    public Optional<PDI> findPDI(Long id) {
        try (Session session = sessionFactory.openSession()) {
            var pdi = session.find(PDI.class, id);
            return Optional.ofNullable(pdi);
        }
    }

    public List<PDI> findAllPDIs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from PDI", PDI.class).getResultList();
        }
    }

    public PDI updatePDI(Long id, PDI instance) throws ServerReplyException {
        try (Session session = sessionFactory.openSession()) {

            PDI pdi = session.byId(PDI.class)
                    .loadOptional(id)
                    .orElseThrow(() -> new NotFoundException("PDI with id: " + id + " does not exists"));


            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                pdi.updatePDI(instance);
                session.merge(pdi);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadReqException("Could not update PDI.");
            }
            return pdi;
        }
    }
}
