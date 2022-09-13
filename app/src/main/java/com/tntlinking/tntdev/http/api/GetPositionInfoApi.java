package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;

import java.util.List;

public final class GetPositionInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "manpower-rest-api/position/info";
    }

    private int positionId;

    public GetPositionInfoApi setPositionId(int positionId) {
        this.positionId = positionId;
        return this;
    }

    public final static class Bean {

            private int id;
            private int companyId;
            private int recruiterId;
            private String careerDirection;
            private int careerDirectionId;
            private String title;
            private String description;
            private int recruitCount;
            private String workOper;
            private String education;
            private int educationId;
            private String trainingMode;
            private int trainingModeId;
            private String workYears;
            private int workYearsId;
            private String createDate;
            private int status;
            private String workDaysModeName;
            private int workDaysMode;
            private Double startPay;
            private Double endPay;
            private Boolean industryMandatory;
            private List<String> skills;
            private List<SkillsListBean> skillsList;
            private CompanyBean company;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public int getRecruiterId() {
                return recruiterId;
            }

            public void setRecruiterId(int recruiterId) {
                this.recruiterId = recruiterId;
            }

            public String getCareerDirection() {
                return careerDirection;
            }

            public void setCareerDirection(String careerDirection) {
                this.careerDirection = careerDirection;
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

            public String getWorkOper() {
                return workOper;
            }

            public void setWorkOper(String workOper) {
                this.workOper = workOper;
            }

            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;
            }

            public int getEducationId() {
                return educationId;
            }

            public void setEducationId(int educationId) {
                this.educationId = educationId;
            }

            public String getTrainingMode() {
                return trainingMode;
            }

            public void setTrainingMode(String trainingMode) {
                this.trainingMode = trainingMode;
            }

            public int getTrainingModeId() {
                return trainingModeId;
            }

            public void setTrainingModeId(int trainingModeId) {
                this.trainingModeId = trainingModeId;
            }

            public String getWorkYears() {
                return workYears;
            }

            public void setWorkYears(String workYears) {
                this.workYears = workYears;
            }

            public int getWorkYearsId() {
                return workYearsId;
            }

            public void setWorkYearsId(int workYearsId) {
                this.workYearsId = workYearsId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getWorkDaysModeName() {
                return workDaysModeName;
            }

            public void setWorkDaysModeName(String workDaysModeName) {
                this.workDaysModeName = workDaysModeName;
            }

            public int getWorkDaysMode() {
                return workDaysMode;
            }

            public void setWorkDaysMode(int workDaysMode) {
                this.workDaysMode = workDaysMode;
            }

            public Double getStartPay() {
                return startPay;
            }

            public void setStartPay(Double startPay) {
                this.startPay = startPay;
            }

            public Double getEndPay() {
                return endPay;
            }

            public void setEndPay(Double endPay) {
                this.endPay = endPay;
            }

            public Boolean getIndustryMandatory() {
                return industryMandatory;
            }

            public void setIndustryMandatory(Boolean industryMandatory) {
                this.industryMandatory = industryMandatory;
            }

            public List<String> getSkills() {
                return skills;
            }

            public void setSkills(List<String> skills) {
                this.skills = skills;
            }

            public List<SkillsListBean> getSkillsList() {
                return skillsList;
            }

            public void setSkillsList(List<SkillsListBean> skillsList) {
                this.skillsList = skillsList;
            }

            public CompanyBean getCompany() {
                return company;
            }

            public void setCompany(CompanyBean company) {
                this.company = company;
            }

            public static class CompanyBean {
                private int id;
                private String companyName;
                private int status;
                private String taxInvoice;
                private int checkoutType;
                private String createDate;
                private String lastModifyDate;
                private String createUser;
                private String lastModifyUser;
                private String shortName;
                private Integer industryId;
                private String industry;
                private int personSizeId;
                private String personSize;
                private String teamToolsDescId;
                private List<String> teamToolsDesc;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCompanyName() {
                    return companyName;
                }

                public void setCompanyName(String companyName) {
                    this.companyName = companyName;
                }

                public Integer getStatus() {
                    return status;
                }

                public void setStatus(Integer status) {
                    this.status = status;
                }

                public String getTaxInvoice() {
                    return taxInvoice;
                }

                public void setTaxInvoice(String taxInvoice) {
                    this.taxInvoice = taxInvoice;
                }

                public int getCheckoutType() {
                    return checkoutType;
                }

                public void setCheckoutType(int checkoutType) {
                    this.checkoutType = checkoutType;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getLastModifyDate() {
                    return lastModifyDate;
                }

                public void setLastModifyDate(String lastModifyDate) {
                    this.lastModifyDate = lastModifyDate;
                }

                public String getCreateUser() {
                    return createUser;
                }

                public void setCreateUser(String createUser) {
                    this.createUser = createUser;
                }

                public String getLastModifyUser() {
                    return lastModifyUser;
                }

                public void setLastModifyUser(String lastModifyUser) {
                    this.lastModifyUser = lastModifyUser;
                }

                public String getShortName() {
                    return shortName;
                }

                public void setShortName(String shortName) {
                    this.shortName = shortName;
                }

                public Integer getIndustryId() {
                    return industryId;
                }

                public void setIndustryId(Integer industryId) {
                    this.industryId = industryId;
                }

                public String getIndustry() {
                    return industry;
                }

                public void setIndustry(String industry) {
                    this.industry = industry;
                }

                public Integer getPersonSizeId() {
                    return personSizeId;
                }

                public void setPersonSizeId(Integer personSizeId) {
                    this.personSizeId = personSizeId;
                }

                public String getPersonSize() {
                    return personSize;
                }

                public void setPersonSize(String personSize) {
                    this.personSize = personSize;
                }

                public String getTeamToolsDescId() {
                    return teamToolsDescId;
                }

                public void setTeamToolsDescId(String teamToolsDescId) {
                    this.teamToolsDescId = teamToolsDescId;
                }

                public List<String> getTeamToolsDesc() {
                    return teamToolsDesc;
                }

                public void setTeamToolsDesc(List<String> teamToolsDesc) {
                    this.teamToolsDesc = teamToolsDesc;
                }
            }

            public static class SkillsListBean {
                private int id;
                private String skillName;
                private int parentId;
                private String createDate;
                private int status;
                private int typeId;
                private int developerId;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSkillName() {
                    return skillName;
                }

                public void setSkillName(String skillName) {
                    this.skillName = skillName;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getTypeId() {
                    return typeId;
                }

                public void setTypeId(int typeId) {
                    this.typeId = typeId;
                }

                public int getDeveloperId() {
                    return developerId;
                }

                public void setDeveloperId(int developerId) {
                    this.developerId = developerId;
                }
            }
    }
}
