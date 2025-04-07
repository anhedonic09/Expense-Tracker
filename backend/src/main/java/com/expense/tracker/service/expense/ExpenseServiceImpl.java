package com.expense.tracker.service.expense;

import com.expense.tracker.dto.CommonResponse;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.entity.UserProfile;
import com.expense.tracker.exception.expense.ExpenseCreationException;
import com.expense.tracker.exception.expense.ExpenseNotFoundException;
import com.expense.tracker.exception.expense.ExpenseUpdationException;
import com.expense.tracker.exception.user.UserNotFoundException;
import com.expense.tracker.exception.user.UserUnauthorizedException;
import com.expense.tracker.repository.expense.ExpenseRepository;
import com.expense.tracker.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CommonResponse commonResponse;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, CommonResponse commonResponse){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.commonResponse = commonResponse;
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Override
    public Map<String, Object> savingExpenseDetails(Expense expense, String username) {
        try {
            expense.setCreatedBy(username);
            expense.setCreationDate(LocalDateTime.now());
            expense.setLastUpdatedOn(expense.getCreationDate());
            expenseRepository.save(expense);
            return commonResponse.responseOnSuccess(true, HttpStatus.OK.value(), "expense saved successfully");
        }catch (RuntimeException e){
            throw new ExpenseCreationException("Error while saving expense details");
        }
    }

    @Override
    public Map<String, Object> updateExpenseDetails(Expense expense, String username) {

        Expense existingExpenseDetails = expenseRepository.findByExpenseId(expense.getExpenseId());
        if (existingExpenseDetails != null) {
            if (Boolean.TRUE.equals(existingExpenseDetails.getCreatedBy().equals(username))) {
                existingExpenseDetails.setAmount(expense.getAmount());
                existingExpenseDetails.setCategory(expense.getCategory());
                existingExpenseDetails.setModeOfPayment(expense.getModeOfPayment());
                existingExpenseDetails.setDescription(expense.getDescription());
                existingExpenseDetails.setLastUpdatedOn(LocalDateTime.now());
                LOGGER.info("updating expense details started...");
                int result = expenseRepository.updateExpenseInformation(existingExpenseDetails.getExpenseId(), existingExpenseDetails.getCategory(), existingExpenseDetails.getAmount(),
                        existingExpenseDetails.getDescription(), existingExpenseDetails.getModeOfPayment());
                LOGGER.info("expense details updated...");
                if (result > 0) return commonResponse.responseOnSuccess(true, HttpStatus.OK.value(), "expense data updated successfully");
                else return commonResponse.responseOnError(false, "error while updating expense entry");
            } else {
                throw new UserUnauthorizedException("user is unauthorized for this update");
            }
        }
        throw new ExpenseNotFoundException("no such expense entry found");
    }

    @Override
    public Map<String, Object> deleteExpenseDetails(String expenseId, String username) {
        LOGGER.info("delete expense entry using expenseId {}", expenseId);
        Expense existingExpenseDetails = expenseRepository.findByExpenseId(expenseId);
        if (existingExpenseDetails != null) {
            if (Boolean.TRUE. equals(existingExpenseDetails.getCreatedBy().equals(username))) {
                int result =  expenseRepository.deleteByExpenseId(expenseId);
                if (result != 0) return commonResponse.responseOnSuccess(true, HttpStatus.OK.value(), "expense entry deleted successfully");
                else return commonResponse.responseOnError(false, "error while deleting expense entry");
            }
            throw new UserUnauthorizedException("user don't have permission to delete this entry");
        }
        throw new ExpenseNotFoundException("no such expense entry found");
    }
}
