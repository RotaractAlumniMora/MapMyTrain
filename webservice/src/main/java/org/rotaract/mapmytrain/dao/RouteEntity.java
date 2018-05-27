package org.rotaract.mapmytrain.dao;

import javax.persistence.*;

/**
 * Created by TharinduSK on 23/05/2018.
 */
@Entity
@Table(name = "route", schema = "mapmytraindb", catalog = "")
public class RouteEntity {
    private int routeId;
    private String startLoc;
    private String endLoc;
    private String routeName;

    @Id
    @Column(name = "RouteId")
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Basic
    @Column(name = "StartLoc")
    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    @Basic
    @Column(name = "EndLoc")
    public String getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(String endLoc) {
        this.endLoc = endLoc;
    }

    @Basic
    @Column(name = "RouteName")
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteEntity that = (RouteEntity) o;

        if (routeId != that.routeId) return false;
        if (startLoc != null ? !startLoc.equals(that.startLoc) : that.startLoc != null) return false;
        if (endLoc != null ? !endLoc.equals(that.endLoc) : that.endLoc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = routeId;
        result = 31 * result + (startLoc != null ? startLoc.hashCode() : 0);
        result = 31 * result + (endLoc != null ? endLoc.hashCode() : 0);
        return result;
    }
}
