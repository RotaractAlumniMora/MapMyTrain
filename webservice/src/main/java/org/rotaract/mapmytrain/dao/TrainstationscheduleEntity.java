package org.rotaract.mapmytrain.dao;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by TharinduSK on 29/05/2018.
 */
@Entity
@Table(name = "trainstationschedule", schema = "mapmytrain", catalog = "")
public class TrainstationscheduleEntity {
    private int id;
    private int trainId;
    private int day;
    private Time arrivalTime;
    private Time departureTime;
    private String station;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "Day", nullable = false)
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Basic
    @Column(name = "ArrivalTime", nullable = false)
    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "DepartureTime", nullable = false)
    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    @Basic
    @Column(name = "Station", nullable = false, length = 45)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainstationscheduleEntity that = (TrainstationscheduleEntity) o;

        if (id != that.id) return false;
        if (trainId != that.trainId) return false;
        if (day != that.day) return false;
        if (arrivalTime != null ? !arrivalTime.equals(that.arrivalTime) : that.arrivalTime != null) return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;
        if (station != null ? !station.equals(that.station) : that.station != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + trainId;
        result = 31 * result + day;
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (station != null ? station.hashCode() : 0);
        return result;
    }
}
