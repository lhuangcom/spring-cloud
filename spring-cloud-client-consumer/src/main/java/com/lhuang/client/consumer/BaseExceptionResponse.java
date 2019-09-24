package com.lhuang.client.consumer;

import java.sql.Timestamp;

/**
 * 封装调用者返回的异常信息
 * @author LHuang
 * @since 2019/5/7
 */

public class BaseExceptionResponse {

    private Timestamp timestamp;

    private Integer status;

    private String error;

    private Exception exception;

    private String message;

    private String path;

    @Override
    public String toString() {
        return "BaseExceptionResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", exception=" + exception +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
