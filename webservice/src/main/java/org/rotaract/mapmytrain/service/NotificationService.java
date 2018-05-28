package org.rotaract.mapmytrain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationService {

    public List getTrains() {

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        List trains = new ArrayList<String>();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("Select t.trainId, t.startLoc,t.endLoc, t.startTime, t.endTime from TrainEntity t");

            trains = query.getResultList();

            entityManager.close();

        } catch (Exception e) {
            trains.add(Constant.Status.ERROR);
        } finally {
            factory.close();
        }

        return trains;
    }

    public JsonArray getNewsHeadlines() {

        JsonArray newsHeadlineList = new JsonArray();
        JsonObject headline1 = new JsonObject();
        headline1.addProperty("route", "10");
        headline1.addProperty("train", "1075 Yaldevi");
        headline1.addProperty("news_type", "Delay");

        JsonObject headline2 = new JsonObject();
        headline2.addProperty("route", "05");
        headline2.addProperty("train", "1456 Udarata Menike");
        headline2.addProperty("news_type", "Cancellation");

        newsHeadlineList.add(headline1);
        newsHeadlineList.add(headline2);

        return newsHeadlineList;

    }

    public JsonObject getNewsDeatils(String json) {

        JsonObject news = new JsonObject();
        news.addProperty("route", "10");
        news.addProperty("train", "1075 Yaldevi");
        news.addProperty("train_type", "Commute");
        news.addProperty("start_loc", "Colombo Fort");
        news.addProperty("end_loc", "Avissawella");
        news.addProperty("star_time", "10.30 a.m");
        news.addProperty("news_type", "Delay");
        news.addProperty("delay", "2 h");

        return news;

    }

}
