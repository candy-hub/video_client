package com.video.response;


public class MyException  extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String errorCode;

    private boolean propertiesKey = true;


    public MyException(String message) {
        super(message);
    }


    public MyException(String errorCode, String message) {
        this(errorCode, message, true);
    }


    public MyException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }


    public MyException(String errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }


    public MyException(String errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }


    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }
}
