package org.rotaract.mapmytrain.dao;

import javax.persistence.*;

/**
 * Created by TharinduSK on 28/05/2018.
 */
@Entity
@Table(name = "subscribe", schema = "mapmytrain", catalog = "")
public class SubscribeEntity {
    private int id;
    private int routeId;
    private int userId;
    private String startLoc;
    private String endLoc;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "UserId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        SubscribeEntity that = (SubscribeEntity) o;

        if (id != that.id) return false;
        if (routeId != that.routeId) return false;
        if (userId != that.userId) return false;
        if (startLoc != null ? !startLoc.equals(that.startLoc) : that.startLoc != null) return false;
        if (endLoc != null ? !endLoc.equals(that.endLoc) : that.endLoc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + routeId;
        result = 31 * result + userId;
        result = 31 * result + (startLoc != null ? startLoc.hashCode() : 0);
        result = 31 * result + (endLoc != null ? endLoc.hashCode() : 0);
        return result;
    }
}
