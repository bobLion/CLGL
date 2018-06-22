package com.bob.android.clgl.entity;

/**
 * @package com.bob.android.clgl.entity
 * @fileName RemindEntity
 * @Author Bob on 2018/6/20 1:02.
 * @Describe TODO
 */

public class RemindEntity {

    /**
     * code : 0
     * data : {"ticketId":5,"userId":9,"ticketType":"11","ticketNum":401,"createTime":"20180615235112345","createUser":1,"updateTime":"20180620010251768","updateUser":1,"areaId":0}
     * msg : 查询剩余票数
     * count : 0
     * state : 1
     */

    private int code;
    private DataBean data;
    private String msg;
    private int count;
    private String state;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * ticketId : 5
         * userId : 9
         * ticketType : 11
         * ticketNum : 401
         * createTime : 20180615235112345
         * createUser : 1
         * updateTime : 20180620010251768
         * updateUser : 1
         * areaId : 0
         */

        private int ticketId;
        private int userId;
        private String ticketType;
        private int ticketNum;
        private String createTime;
        private int createUser;
        private String updateTime;
        private int updateUser;
        private int areaId;

        public int getTicketId() {
            return ticketId;
        }

        public void setTicketId(int ticketId) {
            this.ticketId = ticketId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTicketType() {
            return ticketType;
        }

        public void setTicketType(String ticketType) {
            this.ticketType = ticketType;
        }

        public int getTicketNum() {
            return ticketNum;
        }

        public void setTicketNum(int ticketNum) {
            this.ticketNum = ticketNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(int updateUser) {
            this.updateUser = updateUser;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }
    }
}
