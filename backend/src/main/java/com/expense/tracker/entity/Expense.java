package com.expense.tracker.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "expenseId", updatable = false, nullable = false)
    private String expenseId;
    @Column(nullable = false)
    private Double amount;
    private String category;
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @Column(nullable = false)
    private LocalDateTime lastUpdatedOn;
    private String modeOfPayment;
    private String description;
    private String createdBy;

    public Expense() {}

    public Expense(String expenseId, Double amount, String category, LocalDateTime creationDate, LocalDateTime lastUpdatedOn, String modeOfPayment, String description, String createdBy) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.category = category;
        this.creationDate = creationDate;
        this.lastUpdatedOn = lastUpdatedOn;
        this.modeOfPayment = modeOfPayment;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
