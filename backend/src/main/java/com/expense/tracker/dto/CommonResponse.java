package com.expense.tracker.dto;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommonResponse<T> {

    public Map<String, Object> responseOnSuccess(T data, int statusCode, String msg){
        Map<String, Object> result = new HashMap<>();
        result.put("message", msg);
        result.put("statusCode", statusCode);
        result.put("result", data);
        return result;
    }
    public Map<String, Object> responseOnError(T result, String msg){
        Map<String, Object> errResult = new HashMap<>();
        errResult.put("message", msg);
//        result.put("statusCode", statusCode);
        errResult.put("result", result);
        return errResult;
    }

}
