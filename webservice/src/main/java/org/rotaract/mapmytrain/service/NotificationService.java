package org.rotaract.mapmytrain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rotaract.mapmytrain.dao.NewsEntity;
import org.rotaract.mapmytrain.dao.NewstypeEntity;
import org.rotaract.mapmytrain.dao.UserEntity;
import org.springframework.stereotype.Component;

import org.rotaract.mapmytrain.dao.TrainEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationService {

    private JsonParser jsonParser = new JsonParser();

    public JsonArray getAllTrains() {

        EntityManagerFactory factory;

        JsonArray trainList = new JsonArray();

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT t FROM TrainEntity t");

            List trains = query.getResultList();

            for (Object train:trains) {

                JsonObject trainInfo = new JsonObject();

                trainInfo.addProperty("train_id", ((TrainEntity) train).getTrainId());
                trainInfo.addProperty("name", ((TrainEntity) train).getName());
                trainInfo.addProperty("start_loc", ((TrainEntity) train).getStartLoc());
                trainInfo.addProperty("end_loc", ((TrainEntity) train).getEndLoc());
                trainInfo.addProperty("start_time", ((TrainEntity) train).getStartTime().toString());
                trainInfo.addProperty("end_time", ((TrainEntity) train).getEndTime().toString());

                trainList.add(trainInfo);

            }

            entityManager.close();


        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty(Constant.Status.STATUS, getStackTrace(e));
            trainList.add(error);
        } finally {
            factory.close();
        }

        return trainList;
    }


    public JsonArray getRouteTrains(String json) {

        EntityManagerFactory factory;

        JsonArray trainList = new JsonArray();
        JsonObject trainJson = (JsonObject) jsonParser.parse(json);

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT t FROM TrainEntity t WHERE t.routeId IS "+ "'"
                    + trainJson.get("route_id").getAsInt() + "'");

            List trains = query.getResultList();

            for (Object train:trains) {

                JsonObject trainInfo = new JsonObject();

                trainInfo.addProperty("train_id", ((TrainEntity) train).getTrainId());
                trainInfo.addProperty("name", ((TrainEntity) train).getName());
                trainInfo.addProperty("start_loc", ((TrainEntity) train).getStartLoc());
                trainInfo.addProperty("end_loc", ((TrainEntity) train).getEndLoc());
                trainInfo.addProperty("start_time", ((TrainEntity) train).getStartTime().toString());
                trainInfo.addProperty("end_time", ((TrainEntity) train).getEndTime().toString());

                trainList.add(trainInfo);

            }

            entityManager.close();


        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty(Constant.Status.STATUS, getStackTrace(e));
            trainList.add(error);
        } finally {
            factory.close();
        }

        return trainList;
    }

    public List getTrainStations(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        JsonObject trainJson = (JsonObject) jsonParser.parse(json);
        List stations = new ArrayList<String>();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery("SELECT Station FROM trainstationschedule WHERE TrainId= " + "'"
                        + trainJson.get("train_id").getAsInt() + "'");

            stations = query.getResultList();

            entityManager.close();

        } catch (Exception e) {
            stations.add(getStackTrace(e));
        } finally {
            factory.close();
        }

        return stations;
    }


    public String addNews(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();

        String resMsg = Constant.Status.SUCCESS;
        JsonObject newsJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction( ).begin( );

            Query query = entityManager.createQuery( "SELECT e FROM UserEntity e WHERE e.phoneNum IS " + "'"
                    + newsJson.get("phone_num").getAsString() + "'");

            UserEntity user = (UserEntity)query.getSingleResult();

            query = entityManager.createQuery( "SELECT n FROM NewstypeEntity n WHERE n.category=" + "'"
                    + newsJson.get("news_type").getAsString().toLowerCase() + "'");

            NewstypeEntity newstypeEntity = (NewstypeEntity)query.getSingleResult();

            NewsEntity newsEntity = new NewsEntity();

            newsEntity.setUserId(user.getUserId());
            newsEntity.setTrainId(newsJson.get("train_id").getAsInt());
            newsEntity.setNewsTypeId(newstypeEntity.getId());
            newsEntity.setMessage(newsJson.get("message").getAsString());
            newsEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));

            entityManager.persist(newsEntity);
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
