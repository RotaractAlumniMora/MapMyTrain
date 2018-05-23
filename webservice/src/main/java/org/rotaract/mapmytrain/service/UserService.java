package org.rotaract.mapmytrain.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.rotaract.mapmytrain.dao.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class UserService {

    private JsonParser jsonParser = new JsonParser();

    public String addUser(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        int userId = 0;
        String resMsg = Constant.Status.SUCCESS;
        JsonObject userJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction( ).begin( );
            UserEntity user = new UserEntity();
            user.setName(userJson.get("name").getAsString());
            user.setPhoneNum(userJson.get("phonenumber").getAsString());

            entityManager.persist(user);
            entityManager.getTransaction().commit();
            entityManager.close( );

        } catch (Exception e) {
            resMsg = getStackTrace(e);
        } finally {
           factory.close();
        }

        return resMsg;
    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
