package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.io.Serializable;
import java.util.List;

/**
 * desc   : 获取企业管理员电话号码
 */
public final class GetPositionOriginalApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/position/original";
    }

    private int positionId;

    public GetPositionOriginalApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public final static class Bean implements Serializable {
        private int id;
        private int careerDirectionId;
        private String title;
        private String description;
        private int recruitCount;
        private int workOperId;
        private int workYearsId;
        private int educationId;
        private int trainingModeId;
        private int workDaysMode;
        private double startPay;
        private double endPay;
        private int industryMandatory;
        private List<Integer> skillIds;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCareerDirectionId() {
            return careerDirectionId;
        }

        public void setCareerDirectionId(int careerDirectionId) {
            this.careerDirectionId = careerDirectionId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getRecruitCount() {
            return recruitCount;
        }

        public void setRecruitCount(int recruitCount) {
            this.recruitCount = recruitCount;
        }

        public int getWorkOperId() {
            return workOperId;
        }

        public void setWorkOperId(int workOperId) {
            this.workOperId = workOperId;
        }

        public int getWorkYearsId() {
            return workYearsId;
        }

        public void setWorkYearsId(int workYearsId) {
            this.workYearsId = workYearsId;
        }

        public int getEducationId() {
            return educationId;
        }

        public void setEducationId(int educationId) {
            this.educationId = educationId;
        }

        public int getTrainingModeId() {
            return trainingModeId;
        }

        public void setTrainingModeId(int trainingModeId) {
            this.trainingModeId = trainingModeId;
        }

        public int getWorkDaysMode() {
            return workDaysMode;
        }

        public void setWorkDaysMode(int workDaysMode) {
            this.workDaysMode = workDaysMode;
        }

        public double getStartPay() {
            return startPay;
        }

        public void setStartPay(double startPay) {
            this.startPay = startPay;
        }

        public double getEndPay() {
            return endPay;
        }

        public void setEndPay(double endPay) {
            this.endPay = endPay;
        }

        public int getIndustryMandatory() {
            return industryMandatory;
        }

        public void setIndustryMandatory(int industryMandatory) {
            this.industryMandatory = industryMandatory;
        }

        public List<Integer> getSkillIds() {
            return skillIds;
        }

        public void setSkillIds(List<Integer> skillIds) {
            this.skillIds = skillIds;
        }
    }
}