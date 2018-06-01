package org.rotaract.mapmytrain.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rotaract.mapmytrain.dao.NewsEntity;
import org.rotaract.mapmytrain.dao.NewstypeEntity;
import org.rotaract.mapmytrain.dao.TrainEntity;
import org.rotaract.mapmytrain.dao.UserEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class NotificationService {

    private JsonParser jsonParser = new JsonParser();

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

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

            for (Object train : trains) {

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

            Query query = entityManager.createQuery("SELECT t FROM TrainEntity t WHERE t.routeId IS " + "'"
                    + trainJson.get("route_id").getAsInt() + "'");

            List routeTrains = query.getResultList();

            for (Object routeTrain : routeTrains) {

                JsonObject routeTrainInfo = new JsonObject();

                routeTrainInfo.addProperty("train_id", ((TrainEntity) routeTrain).getTrainId());
                routeTrainInfo.addProperty("name", ((TrainEntity) routeTrain).getName());
                routeTrainInfo.addProperty("start_loc", ((TrainEntity) routeTrain).getStartLoc());
                routeTrainInfo.addProperty("end_loc", ((TrainEntity) routeTrain).getEndLoc());
                routeTrainInfo.addProperty("start_time", ((TrainEntity) routeTrain).getStartTime().toString());
                routeTrainInfo.addProperty("end_time", ((TrainEntity) routeTrain).getEndTime().toString());

                trainList.add(routeTrainInfo);

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
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT e FROM UserEntity e WHERE e.userId IS " + "'"
                    + newsJson.get("user_id").getAsInt() + "'");

            UserEntity user = (UserEntity) query.getSingleResult();

            query = entityManager.createQuery("SELECT n FROM NewstypeEntity n WHERE n.category=" + "'"
                    + newsJson.get("news_type").getAsString().toLowerCase() + "'");

            NewstypeEntity newstypeEntity = (NewstypeEntity) query.getSingleResult();

            NewsEntity newsEntity = new NewsEntity();

            newsEntity.setUserId(user.getUserId());
            newsEntity.setTrainId(newsJson.get("train_id").getAsInt());
            newsEntity.setNewsTypeId(newstypeEntity.getId());
            newsEntity.setMessage(newsJson.get("message").getAsString());
            newsEntity.setRecordTimestamp(new Timestamp(System.currentTimeMillis()));

            entityManager.persist(newsEntity);
            entityManager.getTransaction().commit();

            entityManager.close();

        } catch (Exception e) {
            resMsg = getStackTrace(e);
        } finally {
            factory.close();
        }

        return resMsg;
    }

//    public JsonArray getSubscriberAlerts(String json) {
//
//        EntityManagerFactory factory;
//
//        JsonArray alertList = new JsonArray();
//        JsonObject alertJson = (JsonObject) jsonParser.parse(json);
//
//        try {
//            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
//        } catch (Throwable ex) {
//            System.err.println("Failed to create sessionFactory object." + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//
//        EntityManager entityManager = factory.createEntityManager();
//
//        try {
//            entityManager.getTransaction().begin();
//
//            Query query = entityManager.createQuery("SELECT e FROM UserEntity e WHERE e.userId IS " + "'"
//                    + alertJson.get("user_id").getAsInt() + "'");
//
//            UserEntity user = (UserEntity) query.getSingleResult();
//
//            query = entityManager.createQuery("SELECT s FROM SubscribeEntity s WHERE s.userId IS " + "'"
//                    + user.getUserId() + "'");
//
//            SubscribeEntity subscription = (SubscribeEntity) query.getSingleResult();
//
//            query = entityManager.createQuery("SELECT s FROM SubscribetimeEntity s WHERE s.subscribeId IS " + "'"
//                    + subscription.getId() + "'");
//
//            List subscriptionTimes = query.getResultList();
//
//            Boolean isNotificationDay = false;
//
//            Calendar c = Calendar.getInstance();
//            c.setTime(new SimpleDateFormat("yyyy-M-dd").parse(alertJson.get("date").getAsString()));
//            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//
//
//            for (Object element : subscriptionTimes) {
//
//                SubscribetimeEntity subscribetimeEntity = (SubscribetimeEntity) element;
//
//                if (subscribetimeEntity.getDay() == 1) {
//                    if (dayOfWeek >= 2 && dayOfWeek <= 6) {
//                        isNotificationDay = true;
//                        break;
//                    }
//                } else if (subscribetimeEntity.getDay() == 2) {
//                    if (dayOfWeek == 7) {
//                        isNotificationDay = true;
//                        break;
//                    }
//                } else if (subscribetimeEntity.getDay() == 3) {
//                    if (dayOfWeek == 1) {
//                        isNotificationDay = true;
//                        break;
//                    }
//                } else (subscribetimeEntity.getDay() == 4)
//                {
//                    query = entityManager.createQuery("SELECT h FROM HolidayEntity h WHERE h.date IS " + "'"
//                            + alertJson.get("date").getAsString() + "'");
//
//                    List holidays = query.getResultList();
//
//                    if (!holidays.isEmpty()) {
//                        isNotificationDay = true;
//                        break;
//                    }
//
//                }
//
//            }
//
//            if (isNotificationDay) {
//
//                query = entityManager.createQuery("SELECT t FROM TrainEntity t WHERE t.routeId IS " + "'"
//                        + subscription.getRouteId() + "'");
//
//                List subscribedTrains = query.getResultList();
//
//                Time startTime = new Time(0);
//                Time endTime = new Time(0);
//
//                for (Object element : subscriptionTimes) {
//
//                    SubscribetimeEntity userSubscribeTime = (SubscribetimeEntity) element;
//
//                    if (userSubscribeTime.getDay() == 1 && dayOfWeek >= 2 && dayOfWeek <= 6) {
//                        startTime = userSubscribeTime.getStartTime();
//                        endTime = userSubscribeTime.getEndTime();
//                        break;
//
//                    } else if (userSubscribeTime.getDay() == 2 && dayOfWeek == 7) {
//
//                        startTime = userSubscribeTime.getStartTime();
//                        endTime = userSubscribeTime.getEndTime();
//                        break;
//
//                    } else (userSubscribeTime.getDay() == 3 && dayOfWeek == 1) {
//                        startTime = userSubscribeTime.getStartTime();
//                        endTime = userSubscribeTime.getEndTime();
//                        break;
//
//                    }
//
//                }
//
//                startTime.getTime();
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//                String timestampString = alertJson.get("date").getAsString() + " " + startTime.toString();
//                Date parsedDate = dateFormat.parse(timestampString);
//
//                Timestamp startTimestamp = new java.sql.Timestamp(parsedDate.getTime());
//
//                timestampString = alertJson.get("date").getAsString() + " " + endTime.toString();
//                parsedDate = dateFormat.parse(timestampString);
//
//                Timestamp endTimestamp = new java.sql.Timestamp(parsedDate.getTime());
//
//                query = entityManager.createQuery("SELECT nt FROM NewstypeEntity nt");
//
//                List newsTypes = query.getResultList();
//
//                for (Object subscribedTrainElement : subscribedTrains) {
//
//                    TrainEntity train = (TrainEntity) subscribedTrainElement;
//
//                    query = entityManager.createQuery("SELECT n FROM NewsEntity n WHERE n.trainId IS " + "'"
//                            + train.getTrainId() + "' AND n.recordTimestamp >=" + "'"
//                            + startTimestamp + "'" + "AND n.recordTimestamp <" + "'"
//                            + endTimestamp + "'");
//
//                    List newsList = query.getResultList();
//
//                    List delayTimeList = new ArrayList<Integer>();
//                    int cancelNotificationCount = 0;
//                    Map<String, String> lateDepNotificationMap = new HashMap<String, String>();
//
//                    for (Object listElement : newsList) {
//
//                        NewsEntity news = (NewsEntity) listElement;
//
//                        if (news.getNewsTypeId() == 1) {
//
//                            int delayInMin = Integer.parseInt(news.getMessage().split(" ")[0]) * 60
//                                    + Integer.parseInt(news.getMessage().split(" ")[1]);
//
//                            delayTimeList.add(delayInMin);
//
//                        } else if (news.getNewsTypeId() == 2) {
//
//                            cancelNotificationCount++;
//
//                        } else if (news.getNewsTypeId() == 3) {
//
//                            lateDepNotificationMap.put(news.getMessage().split(" ")[0], news.getMessage().split(" ")[1]);
//                        }
//                    }
//
//
//                }
//
////                for (Object train : trains) {
////
////                    JsonObject trainInfo = new JsonObject();
////
////                    trainInfo.addProperty("train_id", ((TrainEntity) train).getTrainId());
////                    trainInfo.addProperty("name", ((TrainEntity) train).getName());
////                    trainInfo.addProperty("start_loc", ((TrainEntity) train).getStartLoc());
////                    trainInfo.addProperty("end_loc", ((TrainEntity) train).getEndLoc());
////                    trainInfo.addProperty("start_time", ((TrainEntity) train).getStartTime().toString());
////                    trainInfo.addProperty("end_time", ((TrainEntity) train).getEndTime().toString());
////
////                    trainList.add(trainInfo);
////
////                }
//            } else {
//                JsonObject error = new JsonObject();
//                error.addProperty(Constant.Status.STATUS, Constant.Status.DAY_NOT_SUBSCRIBED_ERROR);
//
//            }
//
//            entityManager.close();
//
//
//        } catch (Exception e) {
//            JsonObject error = new JsonObject();
//            error.addProperty(Constant.Status.STATUS, getStackTrace(e));
////            trainList.add(error);
//        } finally {
//            factory.close();
//        }
//
//
//        return trainList;
//    }


    public JsonArray searchAlerts(String json) {

        EntityManagerFactory factory;

        JsonArray alertList = new JsonArray();
        JsonObject alertJson = (JsonObject) jsonParser.parse(json);

        try {
            factory = Persistence.createEntityManagerFactory("org.hibernate.mapmytrain.jpa");
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        EntityManager entityManager = factory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT t FROM TrainEntity t WHERE t.routeId IS " + "'"
                    + alertJson.get("route_id").getAsInt() + "'");

            List routeTrains = query.getResultList();

            for (Object subscribedTrainElement : routeTrains) {

                TrainEntity train = (TrainEntity) subscribedTrainElement;

                query = entityManager.createQuery("SELECT n FROM NewsEntity n WHERE n.trainId IS " + "'"
                        + train.getTrainId() + "' AND Date(n.recordTimestamp) =" + "'"
                        + alertJson.get("date").getAsString() + "' ORDER BY n.recordTimestamp DESC");

                List newsList = query.getResultList();

                List delayTimeList = new ArrayList<String>();
                int cancelNotificationCount = 0;
                Map<String, String> lateDepNotificationMap = new HashMap<String, String>();

                List delayTimeStamp = new ArrayList<Timestamp>();
                List cancelTimeStamp = new ArrayList<Timestamp>();
                List lateDepTimeStamp = new ArrayList<Timestamp>();

                for (Object listElement : newsList) {

                    NewsEntity news = (NewsEntity) listElement;

                    if (news.getNewsTypeId() == 1) {

//                            int delayInMin = Integer.parseInt();

                        delayTimeList.add(news.getMessage());
                        delayTimeStamp.add(news.getRecordTimestamp());

                    } else if (news.getNewsTypeId() == 2) {

                        cancelNotificationCount++;
                        cancelTimeStamp.add(news.getRecordTimestamp());

                    } else if (news.getNewsTypeId() == 3) {

                        lateDepNotificationMap.put(news.getMessage().split(" ")[0], news.getMessage().split(" ")[1]);
                        lateDepTimeStamp.add(news.getRecordTimestamp());
                    }

                }

                if (alertJson.get("alert_type").getAsInt() == 0) {

                    if (!delayTimeList.isEmpty() && delayTimeList.size() > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {
                        JsonObject delayJson = new JsonObject();

                        delayJson.addProperty("alert_type", "Delay");
                        delayJson.addProperty("train_id", train.getTrainId());
                        delayJson.addProperty("train_name", train.getName());
                        delayJson.addProperty("message", delayTimeList.get(0).toString());

                        String originalString = delayTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        delayJson.addProperty("time", time);

                        alertList.add(delayJson);

                    } else if (cancelNotificationCount > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {

                        JsonObject cancelJson = new JsonObject();

                        cancelJson.addProperty("alert_type", "Cancelled");
                        cancelJson.addProperty("train_id", train.getTrainId());
                        cancelJson.addProperty("train_name", train.getName());
                        cancelJson.addProperty("message", "");

                        String originalString = cancelTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        cancelJson.addProperty("time", time);

                        alertList.add(cancelJson);

                    } else if (!lateDepNotificationMap.isEmpty() && lateDepNotificationMap.size() > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {

                        JsonObject lateDepJson = new JsonObject();

                        lateDepJson.addProperty("alert_type", "Late Departure");
                        lateDepJson.addProperty("train_id", train.getTrainId());
                        lateDepJson.addProperty("train_name", train.getName());

                        Map.Entry<String, String> entry = lateDepNotificationMap.entrySet().iterator().next();
                        String key = entry.getKey();
                        String value = entry.getValue();

                        lateDepJson.addProperty("message", key);

                        String originalString = lateDepTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        lateDepJson.addProperty("time", time);

                        alertList.add(lateDepJson);

                    }


                } else if (alertJson.get("alert_type").getAsInt() == 1) {

                    if (!delayTimeList.isEmpty() && delayTimeList.size() > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {

                        JsonObject delayJson = new JsonObject();

                        delayJson.addProperty("alert_type", "Delay");
                        delayJson.addProperty("train_id", train.getTrainId());
                        delayJson.addProperty("train_name", train.getName());
                        delayJson.addProperty("message", delayTimeList.get(0).toString());

                        String originalString = delayTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        delayJson.addProperty("time", time);

                        alertList.add(delayJson);

                    }

                } else if (alertJson.get("alert_type").getAsInt() == 2) {

                    if (cancelNotificationCount > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {

                        JsonObject cancelJson = new JsonObject();

                        cancelJson.addProperty("alert_type", "Cancelled");
                        cancelJson.addProperty("train_id", train.getTrainId());
                        cancelJson.addProperty("train_name", train.getName());
                        cancelJson.addProperty("message", "");

                        String originalString = cancelTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        cancelJson.addProperty("time", time);

                        alertList.add(cancelJson);

                    }

                } else if (alertJson.get("alert_type").getAsInt() == 3) {

                    if (!lateDepNotificationMap.isEmpty() && lateDepNotificationMap.size() > Constant.NotificationConfig.RELIABILITY_THRESHOLD) {

                        JsonObject lateDepJson = new JsonObject();

                        lateDepJson.addProperty("alert_type", "Late Departure");
                        lateDepJson.addProperty("train_id", train.getTrainId());
                        lateDepJson.addProperty("train_name", train.getName());

                        Map.Entry<String, String> entry = lateDepNotificationMap.entrySet().iterator().next();
                        String key = entry.getKey();
                        String value = entry.getValue();

                        lateDepJson.addProperty("message", key);

                        String originalString = lateDepTimeStamp.get(0).toString();
                        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                        String time = new SimpleDateFormat("H:mm").format(date);
                        lateDepJson.addProperty("time", time);

                        alertList.add(lateDepJson);

                    }

                }

            }

            entityManager.close();


        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty(Constant.Status.STATUS, getStackTrace(e));
            alertList.add(error);
        } finally {
            factory.close();
        }

        return alertList;
    }

}
