package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory factory = Util.getSessionFactory();


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE  IF NOT EXISTS user " +
                            " ( Id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User создана");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE  IF  EXISTS user ")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User удалена");
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User под номером – " + id + " удалён");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            for (User e : users)
                System.out.println(e);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user")
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User очищена");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }
}
