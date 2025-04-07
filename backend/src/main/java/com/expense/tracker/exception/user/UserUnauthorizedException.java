package com.expense.tracker.exception.user;

public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String msg){super(msg);}
}
