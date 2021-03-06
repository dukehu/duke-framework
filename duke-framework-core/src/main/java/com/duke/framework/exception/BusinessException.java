package com.duke.framework.exception;

import com.duke.framework.web.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created duke on 2018/6/28
 */
public class BusinessException extends RuntimeException {

    private Integer status;

    private String code;

    private String message;

    private List<FieldError> fieldErrors = new ArrayList<>();

    public BusinessException(FieldError fieldError) {
        this("数据校验失败");
        this.fieldErrors.add(fieldError);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, List<FieldError> fieldErrors) {
        this(message);
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
