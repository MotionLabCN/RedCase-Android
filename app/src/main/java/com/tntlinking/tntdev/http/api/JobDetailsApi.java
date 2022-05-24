package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

public final class JobDetailsApi implements IRequestApi {

    @Override
    public String getApi() {
        return "developer_recommends/position_info";
    }

    private String positionId;


    public JobDetailsApi setpositionId(String positionId) {
        this.positionId = positionId;
        return this;
    }

    public final static class Bean {
        private String careerDirection;
        private CompanyBean company;
        private int companyId;
        private String createDate;
        private String description;
        private String education;
        private int endPay;
        private int id;
        private String recruitCount;
        private int recruiterId;
        private int startPay;
        private int status;
        private String title;
        private String trainingMode;
        private String workDaysMode;
        private String workDaysModeName;
        private String workYearsName;
        private String educationName;
        private List<String> skillNames;
        private String companyIndustryName;

        public String getCompanyPersonSizeName() {
            return companyPersonSizeName;
        }

        public void setCompanyPersonSizeName(String companyPersonSizeName) {
            this.companyPersonSizeName = companyPersonSizeName;
        }

        private String companyPersonSizeName;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public boolean isSelfRecommendStatus() {
            return selfRecommendStatus;
        }

        private String companyName;

        private List<String> companyTeamToolsDescNames;

        public String getWorkDaysModeName() {
            return workDaysModeName;
        }

        public void setWorkDaysModeName(String workDaysModeName) {
            this.workDaysModeName = workDaysModeName;
        }

        public String getWorkYearsName() {
            return workYearsName;
        }

        public void setWorkYearsName(String workYearsName) {
            this.workYearsName = workYearsName;
        }

        public String getEducationName() {
            return educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName;
        }

        public List<String> getSkillNames() {
            return skillNames;
        }

        public void setSkillNames(List<String> skillNames) {
            this.skillNames = skillNames;
        }

        public String getCompanyIndustryName() {
            return companyIndustryName;
        }

        public void setCompanyIndustryName(String companyIndustryName) {
            this.companyIndustryName = companyIndustryName;
        }

        public List<String> getCompanyTeamToolsDescNames() {
            return companyTeamToolsDescNames;
        }

        public void setCompanyTeamToolsDescNames(List<String> companyTeamToolsDescNames) {
            this.companyTeamToolsDescNames = companyTeamToolsDescNames;
        }

        public boolean getSelfRecommendStatus() {
            return selfRecommendStatus;
        }

        public void setSelfRecommendStatus(boolean selfRecommendStatus) {
            this.selfRecommendStatus = selfRecommendStatus;
        }


        private boolean selfRecommendStatus;


        public String getCareerDirection() {
            return careerDirection;
        }

        public void setCareerDirection(String careerDirection) {
            this.careerDirection = careerDirection;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getEndPay() {
            return endPay;
        }

        public void setEndPay(int endPay) {
            this.endPay = endPay;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRecruitCount() {
            return recruitCount;
        }

        public void setRecruitCount(String recruitCount) {
            this.recruitCount = recruitCount;
        }

        public int getRecruiterId() {
            return recruiterId;
        }

        public void setRecruiterId(int recruiterId) {
            this.recruiterId = recruiterId;
        }


        public int getStartPay() {
            return startPay;
        }

        public void setStartPay(int startPay) {
            this.startPay = startPay;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTrainingMode() {
            return trainingMode;
        }

        public void setTrainingMode(String trainingMode) {
            this.trainingMode = trainingMode;
        }

        public String getWorkDaysMode() {
            return workDaysMode;
        }

        public void setWorkDaysMode(String workDaysMode) {
            this.workDaysMode = workDaysMode;
        }

        public String getWorkOper() {
            return workOper;
        }

        public void setWorkOper(String workOper) {
            this.workOper = workOper;
        }

        public String getWorkYears() {
            return workYears;
        }

        public void setWorkYears(String workYears) {
            this.workYears = workYears;
        }

        private String workOper;
        private String workYears;

    }

    public final static class CompanyBean {
        private String createDate;
        private String createUser;
        private String id;
        private String industry;
        private String industryId;
        private String lastModifyDate;
        private String lastModifyUser;
        private String personSize;
        private String personSizeId;
        private String shortName;
        private String status;
        private String taxInvoice;
        private List<String> teamToolsDesc;


        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getIndustryId() {
            return industryId;
        }

        public void setIndustryId(String industryId) {
            this.industryId = industryId;
        }

        public String getLastModifyDate() {
            return lastModifyDate;
        }

        public void setLastModifyDate(String lastModifyDate) {
            this.lastModifyDate = lastModifyDate;
        }

        public String getLastModifyUser() {
            return lastModifyUser;
        }

        public void setLastModifyUser(String lastModifyUser) {
            this.lastModifyUser = lastModifyUser;
        }

        public String getPersonSize() {
            return personSize;
        }

        public void setPersonSize(String personSize) {
            this.personSize = personSize;
        }

        public String getPersonSizeId() {
            return personSizeId;
        }

        public void setPersonSizeId(String personSizeId) {
            this.personSizeId = personSizeId;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTaxInvoice() {
            return taxInvoice;
        }

        public void setTaxInvoice(String taxInvoice) {
            this.taxInvoice = taxInvoice;
        }

        public List<String> getTeamToolsDesc() {
            return teamToolsDesc;
        }

        public void setTeamToolsDesc(List<String> teamToolsDesc) {
            this.teamToolsDesc = teamToolsDesc;
        }

        public String getTeamToolsDescId() {
            return teamToolsDescId;
        }

        public void setTeamToolsDescId(String teamToolsDescId) {
            this.teamToolsDescId = teamToolsDescId;
        }

        private String teamToolsDescId;

    }
}