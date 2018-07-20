package org.rotaract.mapmytrain.dao;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by TharinduSK on 29/05/2018.
 */
@Entity
@Table(name = "news", schema = "mapmytrain", catalog = "")
public class NewsEntity {
    private int id;
    private int userId;
    private int trainId;
    private int newsTypeId;
    private String message;
    private Timestamp recordTimestamp;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UserId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "TrainId", nullable = false)
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    @Basic
    @Column(name = "NewsTypeId", nullable = false)
    public int getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(int newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    @Basic
    @Column(name = "Message", nullable = true, length = 255)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "RecordTimestamp", nullable = false)
    public Timestamp getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(Timestamp recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsEntity that = (NewsEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (trainId != that.trainId) return false;
        if (newsTypeId != that.newsTypeId) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (recordTimestamp != null ? !recordTimestamp.equals(that.recordTimestamp) : that.recordTimestamp != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + trainId;
        result = 31 * result + newsTypeId;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (recordTimestamp != null ? recordTimestamp.hashCode() : 0);
        return result;
    }
}
