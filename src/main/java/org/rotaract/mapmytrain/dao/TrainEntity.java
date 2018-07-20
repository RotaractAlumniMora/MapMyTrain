package org.rotaract.mapmytrain.dao;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by TharinduSK on 29/05/2018.
 */
@Entity
@Table(name = "train", schema = "mapmytrain", catalog = "")
public class TrainEntity {
    private int trainId;
    private int routeId;
    private String name;
    private String type;
    private Time startTime;
    private Time endTime;
    private String startLoc;
    private String endLoc;

    @Id
    @Column(name = "TrainId", nullable = false)
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    @Basic
    @Column(name = "RouteId", nullable = false)
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Type", nullable = true, length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "StartTime", nullable = true)
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "EndTime", nullable = true)
    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "StartLoc", nullable = true, length = 45)
    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    @Basic
    @Column(name = "EndLoc", nullable = true, length = 45)
    public String getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(String endLoc) {
        this.endLoc = endLoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainEntity that = (TrainEntity) o;

        if (trainId != that.trainId) return false;
        if (routeId != that.routeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (startLoc != null ? !startLoc.equals(that.startLoc) : that.startLoc != null) return false;
        if (endLoc != null ? !endLoc.equals(that.endLoc) : that.endLoc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainId;
        result = 31 * result + routeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (startLoc != null ? startLoc.hashCode() : 0);
        result = 31 * result + (endLoc != null ? endLoc.hashCode() : 0);
        return result;
    }
}
