package com.expense.tracker.repository.expense;

import com.expense.tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {

    @Query(value = "SELECT * FROM expenses WHERE expense_id  = :expenseId", nativeQuery = true)
    Expense findByExpenseId(@Param("expenseId") String expenseId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE expenses SET category = :category, amount = :amount, description = :description, mode_of_payment = :modeOfPayment WHERE expense_id = :expenseId", nativeQuery = true)
    int updateExpenseInformation(
            @Param("expenseId") String expenseId,
            @Param("category") String category,
            @Param("amount") Double amount,
            @Param("description") String description,
            @Param("modeOfPayment") String modeOfPayment);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM expense e WHERE e.expense_id = :expenseId", nativeQuery = true)
    int deleteByExpenseId(@Param("expenseId") String expenseId);
}
