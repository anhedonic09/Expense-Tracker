package com.expense.tracker.entity.dashboard;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "dashboard")
public class Dashboard {


    @EmbeddedId
    private DashboardId id;
    private Double totalIncome;
    private Double totalExpenses;
    @Lob
    private String monthlySavings;
    /*
            ObjectMapper mapper = new ObjectMapper();

            To save
            String json = mapper.writeValueAsString(monthlySavingsList);

            To read
            List<Map<Integer, Double>> list = mapper.readValue(json, new TypeReference<>() {});
    */

    public Dashboard() {}

    public Dashboard(DashboardId id, Double totalIncome, Double totalExpenses, String monthlySavings) {
        this.id = id;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.monthlySavings = monthlySavings;
    }

    public DashboardId getId() {
        return id;
    }

    public void setId(DashboardId id) {
        this.id = id;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(String monthlySavings) {
        this.monthlySavings = monthlySavings;
    }
}
