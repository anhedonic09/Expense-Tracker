package com.expense.tracker.exception.user;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String msg){super(msg);}
}
