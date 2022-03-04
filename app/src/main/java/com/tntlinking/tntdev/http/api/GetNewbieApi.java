package com.tntlinking.tntdev.http.api;

import com.hjq.http.config.IRequestApi;



public final class GetNewbieApi implements IRequestApi {

    @Override
    public String getApi() {
        return "task/get/newbie";
    }


    public class Bean{
        private int developerId;
        private String createDate;
        private int remainingDays;
        private String taskName;
        private String rewardNum;
        private String taskDescription;
        private int taskStatus;  //任务状态 0-未开始 1-进行中 2-已完成 3-已失效
        private int rewardStatus;  //	奖励发放状态：0-不满足 1-待发放 2-已发放

        public int getDeveloperId() {
            return developerId;
        }

        public void setDeveloperId(int developerId) {
            this.developerId = developerId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getRemainingDays() {
            return remainingDays;
        }

        public void setRemainingDays(int remainingDays) {
            this.remainingDays = remainingDays;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getRewardNum() {
            return rewardNum;
        }

        public void setRewardNum(String rewardNum) {
            this.rewardNum = rewardNum;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public int getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(int taskStatus) {
            this.taskStatus = taskStatus;
        }

        public int getRewardStatus() {
            return rewardStatus;
        }

        public void setRewardStatus(int rewardStatus) {
            this.rewardStatus = rewardStatus;
        }
    }
}