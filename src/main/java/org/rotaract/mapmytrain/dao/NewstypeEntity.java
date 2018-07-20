package org.rotaract.mapmytrain.dao;

import javax.persistence.*;

/**
 * Created by TharinduSK on 28/05/2018.
 */
@Entity
@Table(name = "newstype", schema = "mapmytrain", catalog = "")
public class NewstypeEntity {
    private int id;
    private String category;

    @Id
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Category", nullable = false, length = 45)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewstypeEntity that = (NewstypeEntity) o;

        if (id != that.id) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
