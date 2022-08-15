package com.study.ics;

public class Result {

    private String errorCode;
    private String descriptionLink;

    public Result(String errorCode, String descriptionLink){
        this.errorCode = errorCode;
        this.descriptionLink = descriptionLink;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }
}
