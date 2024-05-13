package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class PlayerRepositoryDBTest {
    private SessionFactory sessionFactory;
    private Session session;
    private final PlayerRepositoryDB playerRepositoryDB = new PlayerRepositoryDB();
    private Player testPlayer = new Player(10001L, "Test", "test", Race.ELF, Profession.WARRIOR, new Date(1244497480000L), true, 12);

    @BeforeEach
    void setUp() {
        sessionFactory = new Configuration()
                .configure("db/hibernate.cfg.xml")
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
        session.close();
    }

    @Test
    void getAll() {
        List<Player> all = playerRepositoryDB.getAll(0, 20);
        Assertions.assertNotNull(all);
    }

    @Test
    void getAllCount() {
        Integer count = playerRepositoryDB.getAllCount();
        Assertions.assertNotNull(count);
    }

    @Test
    void save() {
        Transaction transaction = session.beginTransaction();
        Player save = playerRepositoryDB.save(testPlayer);
        Long saveId = save.getId();
        Player founded = playerRepositoryDB.findById(saveId).get();
        Assertions.assertEquals(save, founded);
        transaction.rollback();
    }

    @Test
    void update() {
        Transaction transaction = session.beginTransaction();
        Player save = playerRepositoryDB.save(testPlayer);
        String name = save.getName();
        testPlayer.setName("New Name");
        playerRepositoryDB.update(testPlayer);
        Player get = playerRepositoryDB.findById(save.getId()).get();
        Assertions.assertNotEquals(name, get.getName());
        transaction.rollback();
    }

    @Test
    void findById() {
        Transaction transaction = session.beginTransaction();
        Player save = playerRepositoryDB.save(testPlayer);
        Player find = playerRepositoryDB.findById(save.getId()).get();
        Assertions.assertEquals(save, find);
        transaction.rollback();
    }

    @Test
    void delete() {
        Transaction transaction = session.beginTransaction();
        Player save = playerRepositoryDB.save(testPlayer);
        playerRepositoryDB.delete(save);
        Assertions.assertThrows(NoResultException.class, () -> playerRepositoryDB.findById(save.getId()))  ;
        transaction.rollback();
    }
}