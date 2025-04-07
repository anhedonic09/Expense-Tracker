package com.expense.tracker.controller;

import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.user.UserNotFoundException;
import com.expense.tracker.service.expense.ExpenseService;
import com.expense.tracker.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/expense")
public class Expense {

    private final UserService userService;
    private final ExpenseService expenseService;

    @Autowired
    public Expense(UserService userService, ExpenseService expenseService){
        this.userService = userService;
        this.expenseService = expenseService;
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(Expense.class);

    @PostMapping("/create/{username}")
    public Map<String, Object> saveExpenseDetails(@PathVariable String username, @RequestBody com.expense.tracker.entity.Expense expense){
        LOGGER.info("fetching user details using username: {}", username);
        UserProfile userProfile = userService.findInUserProfileUsingUsername(username);
        LOGGER.info("user details fetched with username: {}", username);
        if (userProfile != null){
            return expenseService.savingExpenseDetails(expense, username);
        }
        LOGGER.info("No such user exist with username: {}", username);
        throw new UserNotFoundException("No such user exist");
    }

    @PutMapping("/update/{username}")
    public Map<String, Object> updateExpenseDetails(@PathVariable String username, @RequestBody com.expense.tracker.entity.Expense expense){
        LOGGER.info("fetching user details using username: {}", username);
        UserProfile userProfile = userService.findInUserProfileUsingUsername(username);
        LOGGER.info("user details fetched with username: {}", username);
        if (userProfile != null){
            return expenseService.updateExpenseDetails(expense, username);
        }
        LOGGER.info("No such user exist with username: {}", username);
        throw new UserNotFoundException("No such user exist");
    }

    @DeleteMapping("/delete/{username}/{expenseId}")
    public Map<String, Object> updateExpenseDetails(@PathVariable(value = "username") String username, @PathVariable(value = "expenseId") String expenseId){
        LOGGER.info("fetching user details using username: {}", username);
        UserProfile userProfile = userService.findInUserProfileUsingUsername(username);
        LOGGER.info("user details fetched with username: {}", username);
        if (userProfile != null){
            return expenseService.deleteExpenseDetails(expenseId, username);
        }
        LOGGER.info("No such user exist with username: {}", username);
        throw new UserNotFoundException("No such user exist");
    }
}
