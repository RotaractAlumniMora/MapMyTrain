package org.rotaract.mapmytrain.dao;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by TharinduSK on 28/05/2018.
 */
@Entity
@Table(name = "holiday", schema = "mapmytrain", catalog = "")
public class HolidayEntity {
    private int id;
    private Date date;
    private String day;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "Day", nullable = true, length = 10)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HolidayEntity that = (HolidayEntity) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (day != null ? !day.equals(that.day) : that.day != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        return result;
    }
}
