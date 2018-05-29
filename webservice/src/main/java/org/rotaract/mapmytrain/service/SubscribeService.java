package org.rotaract.mapmytrain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rotaract.mapmytrain.dao.SubscribetimeEntity;
import org.rotaract.mapmytrain.dao.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubscribeService {

    private JsonParser jsonParser = new JsonParser();

    public String subscribeUser(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        String resMsg = Constant.Status.SUCCESS;
        JsonObject subscriptionJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT e FROM UserEntity e WHERE e.phoneNum IS" + "'"
                    + subscriptionJson.get("phone_num").getAsString() + "'");

            int user_id = ((UserEntity) query.getSingleResult()).getUserId();
            int subscription_id = 0;

            query = entityManager.createQuery("SELECT e FROM SubscribeEntity e");

            List subscription = query.getResultList();

            if (subscription.isEmpty())
            {
                entityManager.createNativeQuery("ALTER TABLE subscribe AUTO_INCREMENT = 1").executeUpdate();
            }

            query = entityManager.createNativeQuery("SELECT Id FROM subscribe WHERE UserId= " + "'"
                    + user_id + "'");

            subscription = query.getResultList();

            if (subscription.isEmpty()) {
                DateFormat formatter = new SimpleDateFormat("HH.mm");

                query = entityManager.createNativeQuery("INSERT INTO subscribe(RouteId, UserId, StartLoc, EndLoc) VALUES (:route_id, :user_id, :start_loc, :end_loc)");

                query.setParameter("route_id", subscriptionJson.get("route_id").getAsInt());
                query.setParameter("user_id", user_id);
                query.setParameter("start_loc", subscriptionJson.get("start_loc").getAsString());
                query.setParameter("end_loc", subscriptionJson.get("end_loc").getAsString());

                query.executeUpdate();

                query = entityManager.createQuery("SELECT s.id FROM SubscribeEntity s ORDER BY s.id DESC");

                subscription = query.setMaxResults(1).getResultList();
                subscription_id = (int) subscription.get(0);

                JsonArray subscriptionTimeList = subscriptionJson.getAsJsonArray("subscription_list");

                List <SubscribetimeEntity> subscribetimeEntityList = new ArrayList<>();
                int i =1;

                for (JsonElement subscription_item:subscriptionTimeList) {

                    SubscribetimeEntity subscribetimeEntity = new SubscribetimeEntity();

                    subscribetimeEntity.setSubscribeId(subscription_id);
                    subscribetimeEntity.setId(i);
                    subscribetimeEntity.setDay(((JsonObject)subscription_item).get("day").getAsInt());
                    subscribetimeEntity.setStartTime(new java.sql.Time(formatter.parse(((JsonObject)subscription_item).get("start_time").getAsString()).getTime()));
                    subscribetimeEntity.setEndTime(new java.sql.Time(formatter.parse(((JsonObject)subscription_item).get("end_time").getAsString()).getTime()));

                    subscribetimeEntityList.add(subscribetimeEntity);
                    i++;
                }

                for (SubscribetimeEntity subscribetimeEntity: subscribetimeEntityList) {

                    entityManager.persist(subscribetimeEntity);

                }

                entityManager.getTransaction().commit();

            } else {
                resMsg = Constant.Status.USER_ALREADY_SUBSCRIBED_ERROR;
            }

            entityManager.close();

        } catch (Exception e) {
            resMsg = getStackTrace(e);
        } finally {
            factory.close();
        }

        return resMsg;
    }

    public List getRoutes() {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        List routes = new ArrayList<String>();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT e.routeId,e.lineName FROM RouteEntity e");

            routes = query.getResultList();

            entityManager.close();

        } catch (Exception e) {
            routes.add(getStackTrace(e));
        } finally {
            factory.close();
        }

        return routes;
    }

    public List getRouteStations(String json) {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        JsonObject routeJson = (JsonObject) jsonParser.parse(json);
        List trainIds;
        List temp_stations;
        List stations = new ArrayList<String>();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery("SELECT TrainId FROM train WHERE RouteId= " + "'"
                    + routeJson.get("route_id").getAsBigInteger() + "'");

            trainIds = query.getResultList();

            for (Object train_id : trainIds) {
                query = entityManager.createNativeQuery("SELECT Station FROM trainstationschedule WHERE TrainId= " + "'"
                        + train_id + "'");

                temp_stations = query.getResultList();

                for (Object station : temp_stations) {

                    stations.add(station);

                }

            }

            entityManager.close();

        } catch (Exception e) {
            stations.add(getStackTrace(e));
        } finally {
            factory.close();
        }

        return stations;
    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
