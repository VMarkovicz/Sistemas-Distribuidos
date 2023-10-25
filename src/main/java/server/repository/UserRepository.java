package server.repository;

import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import server.exception.BadReqException;
import server.exception.ServerReplyException;
import server.models.User;

import java.util.Optional;

public class UserRepository implements Repository{

    private final SessionFactory sessionFactory;

    public UserRepository(){
        this.sessionFactory = (SessionFactory)Persistence.createEntityManagerFactory("server.models");
    }

    public Optional<User> login(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.bySimpleNaturalId(User.class).loadOptional(email);
        }
    }

    @Override
    public void create(User instance) throws ServerReplyException {
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
                throw new BadReqException("User with email " + instance.getEmail() + " already exists");
            }
        }
    }
}
