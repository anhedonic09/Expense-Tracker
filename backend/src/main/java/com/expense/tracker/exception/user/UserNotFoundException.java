package com.expense.tracker.exception.user;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){super(msg);}
}
