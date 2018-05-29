package org.rotaract.mapmytrain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import org.rotaract.mapmytrain.dao.NewsFeedEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by TharinduSK on 29/05/2018.
 */
@Component
public class NewsFeedService {

    private JsonParser jsonParser = new JsonParser();

    public JsonArray getNewsHeadlines(String json) {

        JsonArray newsHeadlineList = new JsonArray();

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        JsonObject newsJson = (JsonObject) jsonParser.parse(json);
        int limit = newsJson.get("limit").getAsInt();
        int iter = 0;

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT n FROM NewsFeedEntity n ORDER BY n.id DESC");

            List newsHeadlines = query.getResultList();

            int threshold = 25;

            entityManager.close();

            for (Object news:newsHeadlines) {

                JsonObject headline = new JsonObject();

                headline.addProperty("id", ((NewsFeedEntity)news).getId());
                headline.addProperty("author",((NewsFeedEntity)news).getAuthor());
                headline.addProperty("project_name",((NewsFeedEntity)news).getProjectName());
                headline.addProperty("url",((NewsFeedEntity)news).getLink());

                String baseline = ((NewsFeedEntity)news).getDescription();
                String[] words = baseline.split(" ");

                baseline = "";
                threshold = threshold>words.length? words.length:threshold;

                for (int i = 0; i < threshold ; i++) {
                    baseline += words[i] + " ";
                }
                baseline = baseline.trim();
                headline.addProperty("baseline",baseline);

                newsHeadlineList.add(headline);
                iter++;

                if (iter >= limit)
                {
                    break;
                }

            }

        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty(Constant.Status.STATUS, getStackTrace(e));
            newsHeadlineList.add(error);
        } finally {
            factory.close();
        }


//        headline1.addProperty("route", "10");
//        headline1.addProperty("train", "1075 Yaldevi");
//        headline1.addProperty("news_type", "Delay");
//
//        JsonObject headline2 = new JsonObject();
//        headline2.addProperty("route", "05");
//        headline2.addProperty("train", "1456 Udarata Menike");
//        headline2.addProperty("news_type", "Cancellation");
//
//        newsHeadlineList.add(headline1);
//        newsHeadlineList.add(headline2);

        return newsHeadlineList;

    }

    public JsonObject getNewsDetails(String json) {

        JsonObject newsDetail = new JsonObject();

        EntityManagerFactory factory;

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();
        JsonObject newsJson = (JsonObject) jsonParser.parse(json);

        try {
            entityManager.getTransaction().begin();

            NewsFeedEntity news = entityManager.find(NewsFeedEntity.class, newsJson.get("id").getAsInt());

            newsDetail.addProperty("author",news.getAuthor());
            newsDetail.addProperty("project_name",news.getProjectName());
            newsDetail.addProperty("url",news.getLink());
            newsDetail.addProperty("desc", news.getDescription());
            newsDetail.addProperty("date",news.getDate().toString());
            newsDetail.addProperty("time", news.getTime().getTime());


            entityManager.close();



        } catch (Exception e) {

            newsDetail.addProperty(Constant.Status.STATUS, Constant.Status.ERROR);

        } finally {
            factory.close();
        }

//        newsDetails.addProperty("route", "10");
//        newsDetails.addProperty("train", "1075 Yaldevi");
//        newsDetails.addProperty("train_type", "Commute");
//        newsDetails.addProperty("start_loc", "Colombo Fort");
//        news.addProperty("end_loc", "Avissawella");
//        news.addProperty("star_time", "10.30 a.m");
//        news.addProperty("news_type", "Delay");
//        news.addProperty("delay", "2 h");

        return newsDetail;

    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
