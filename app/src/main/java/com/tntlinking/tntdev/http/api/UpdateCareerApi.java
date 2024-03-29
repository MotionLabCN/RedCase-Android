package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestType;
import com.hjq.http.model.BodyType;


public final class UpdateCareerApi implements IRequestApi, IRequestType {

    @Override
    public String getApi() {
        return "developer/update/career/info";
    }


    private int careerDirectionId;
    private double curSalary;
    private int workDayMode;
    private int workYearsId;
    private double expectSalary;


    public UpdateCareerApi setCareerDirectionId(int careerDirectionId) {
        this.careerDirectionId = careerDirectionId;
        return this;
    }

    public UpdateCareerApi setCurSalary(double curSalary) {
        this.curSalary = curSalary;
        return this;
    }

    public UpdateCareerApi setExpectSalary(Double expectSalary) {
        this.expectSalary = expectSalary;
        return this;
    }

    public UpdateCareerApi setWorkDayMode(int workDayMode) {
        this.workDayMode = workDayMode;
        return this;
    }

    public UpdateCareerApi setWorkYearsId(int workYearsId) {
        this.workYearsId = workYearsId;
        return this;
    }

    @Override
    public BodyType getType() {
        return BodyType.JSON;
    }

}