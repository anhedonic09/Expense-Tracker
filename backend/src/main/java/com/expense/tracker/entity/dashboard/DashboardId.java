package com.expense.tracker.entity.dashboard;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DashboardId implements Serializable {

    private Integer year;
    private String username;

    public DashboardId() {}

    public DashboardId(Integer year, String username) {
        this.year = year;
        this.username = username;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
