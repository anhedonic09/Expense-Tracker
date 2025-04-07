package com.expense.tracker.exception.expense;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException(String msg){super(msg);}
}
