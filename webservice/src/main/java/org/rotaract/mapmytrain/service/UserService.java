package org.rotaract.mapmytrain.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rotaract.mapmytrain.dao.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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

        String resMsg = Constant.Status.SUCCESS;
        JsonObject userJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction( ).begin( );

            Query query = entityManager.createQuery( "Select e from UserEntity e where e.phoneNum is " + "'"
                            + userJson.get("phone_num").getAsString() + "'");

            List users = query.getResultList();

            if(users.isEmpty())
            {
                UserEntity user = new UserEntity();
                user.setName(userJson.get("name").getAsString());
                user.setPhoneNum(userJson.get("phone_num").getAsString());

                entityManager.persist(user);
                entityManager.getTransaction().commit();
            }else
            {
                resMsg = Constant.Status.USER_ALREADY_EXISTS_ERROR ;
            }

            entityManager.close( );

        } catch (Exception e) {
            resMsg = Constant.Status.ERROR;
        } finally {
           factory.close();
        }

        return resMsg;
    }

    public String updateUserName(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        String resMsg = Constant.Status.SUCCESS;
        JsonObject userJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction( ).begin( );

            Query query = entityManager.createQuery( "Select e from UserEntity e where e.phoneNum is " + "'"
                    + userJson.get("phone_num").getAsString() + "'");

            UserEntity user = (UserEntity) query.getSingleResult();

            user.setName(userJson.get("new_name").getAsString());

            entityManager.persist(user);
            entityManager.getTransaction().commit();

            entityManager.close( );

        } catch (Exception e) {
            resMsg = Constant.Status.ERROR;
        } finally {
            factory.close();
        }

        return resMsg;
    }

    public String updateUserPhone(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        String resMsg = Constant.Status.SUCCESS;
        JsonObject userJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction( ).begin( );

            Query query = entityManager.createQuery( "Select e from UserEntity e where e.phoneNum is " + "'"
                    + userJson.get("old_phone_num").getAsString() + "'");

            UserEntity user = (UserEntity) query.getSingleResult();

            user.setPhoneNum(userJson.get("new_phone_num").getAsString());

            entityManager.persist(user);
            entityManager.getTransaction().commit();

            entityManager.close( );

        } catch (Exception e) {
            resMsg = Constant.Status.ERROR;
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
