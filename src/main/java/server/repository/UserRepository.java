package server.repository;

import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import server.dataTransferObject.UserDTO;
import server.exception.BadReqException;
import server.exception.NotFoundException;
import server.exception.ServerReplyException;
import server.models.User;

import java.util.List;
import java.util.Objects;
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
    @Override
    public void deleteById(Long registro) {
        sessionFactory.inTransaction(session -> {
            User user = session.find(User.class, registro);
            session.createMutationQuery("delete from User where registro = :registro")
                    .setParameter("registro", user.getRegistro())
                    .executeUpdate();
        });
    }
    public boolean tryDelete(Long registro) {
        try (var session = sessionFactory.openSession()) {
            long numberOfAdmins = session.createSelectionQuery("select count(*) from User user where user.tipo = :tipo", Long.class)
                    .setParameter("tipo", true)
                    .uniqueResult();
            if (numberOfAdmins <= 1) {
                return false;
            }
            deleteById(registro);
            return true;
        }
    }
    @Override
    public Optional<User> find(Long id) {
        try (Session session = sessionFactory.openSession()) {
            var user = session.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }
    @Override
    public List<User> findAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }
    @Override
    public User update(Long id, User instance) throws ServerReplyException {
        try (Session session = sessionFactory.openSession()) {

            User user = session.byId(User.class)
                    .loadOptional(id)
                    .orElseThrow(() -> new NotFoundException("Usuario com id: " + id + " não existe"));


            var userWithEmail = session.bySimpleNaturalId(User.class)
                    .loadOptional(instance.getEmail());

            if (userWithEmail.isPresent() && !Objects.equals(userWithEmail.get().getRegistro(), instance.getRegistro())) {
                throw new BadReqException("User with email " + userWithEmail.get().getEmail() + " already exists");
            }


            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user.update(instance);
                session.merge(user);
                tx.commit();
            } catch (RollbackException ignored) {
                if (tx != null) {
                    tx.rollback();
                }
                throw new BadReqException("usuario com email " + user.getEmail() + " ja existe");
            }
            return user;
        }
    }
}
