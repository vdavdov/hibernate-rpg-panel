package com.game.repository;

import com.game.entity.Player;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;

@Getter
@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        sessionFactory = new Configuration()
                .configure("db/hibernate.cfg.xml")
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();

    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<Player> nativeQuery = session.createNativeQuery("select * from player", Player.class);
            nativeQuery.setFirstResult((pageNumber - 1) * pageSize);
            nativeQuery.setMaxResults(pageSize);
            return nativeQuery.list();
        }
    }

    @Override
    public int getAllCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Integer> selectAllPlayerCount = session.createNamedQuery("selectAllPlayerCount", Integer.class);
            return selectAllPlayerCount.uniqueResult();
        }
    }

    @Override
    public Player save(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(player);
                transaction.commit();
                return player;
            } catch (Exception e) {
                transaction.rollback();
            }
            return null;
        }
    }

    @Override
    public Player update(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.update(player);
                transaction.commit();
                return player;
            } catch (Exception e) {
                transaction.rollback();
            }
            return null;
        }
    }

    @Override
    public Optional<Player> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Player> query = session.createQuery("from Player where id = :id", Player.class);
            query.setParameter("id", id);
            return Optional.ofNullable(query.getSingleResult());
        }
    }

    @Override
    public void delete(Player player) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.delete(player);
                transaction.commit();
            }
            catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }

}