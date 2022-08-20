package com.study.ics;

import org.controlsfx.control.RangeSlider;

public class Result {

    public String code;
    public String description;

    public String recommendation;

    public Result(){}

    public Result(String code, String description, String recommendation){
        this.code = code;
        this.description = description;
        this.recommendation = recommendation;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getRecommendation() {
        return recommendation;
    }
}
