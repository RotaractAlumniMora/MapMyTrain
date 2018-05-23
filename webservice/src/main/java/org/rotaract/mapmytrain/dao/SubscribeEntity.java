package org.rotaract.mapmytrain.dao;

import javax.persistence.*;

/**
 * Created by TharinduSK on 23/05/2018.
 */
@Entity
@Table(name = "subscribe")
public class SubscribeEntity {
    private int id;
    private String startLoc;
    private String endLoc;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscribeEntity that = (SubscribeEntity) o;

        if (id != that.id) return false;
        if (startLoc != null ? !startLoc.equals(that.startLoc) : that.startLoc != null) return false;
        if (endLoc != null ? !endLoc.equals(that.endLoc) : that.endLoc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startLoc != null ? startLoc.hashCode() : 0);
        result = 31 * result + (endLoc != null ? endLoc.hashCode() : 0);
        return result;
    }
}
