package com.expense.tracker.service.expense;

import com.expense.tracker.entity.Expense;

import java.util.Map;

public interface ExpenseService {
    Map<String, Object> savingExpenseDetails(Expense expense, String username);

    Map<String, Object> updateExpenseDetails(Expense expense, String username);

    Map<String, Object> deleteExpenseDetails(String expenseId, String username);
}
