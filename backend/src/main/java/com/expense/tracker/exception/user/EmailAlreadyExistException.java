package com.expense.tracker.exception.user;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String msg){super(msg);}
}
