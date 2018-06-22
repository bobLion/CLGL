package com.bob.android.clgl.entity;

import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName TicketCountEntity
 * @Author Bob on 2018/6/20 18:07.
 * @Describe TODO
 */

public class TicketCountEntity {


    /**
     * code : 0
     * data : [{"ticketId":52,"userId":25,"ticketType":"14","ticketNum":100,"createTime":null,"createUser":0,"updateTime":null,"updateUser":0,"areaId":21}]
     * msg : null
     * count : 1
     * state : null
     */

    private int code;
    private Object msg;
    private int count;
    private Object state;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ticketId : 52
         * userId : 25
         * ticketType : 14
         * ticketNum : 100
         * createTime : null
         * createUser : 0
         * updateTime : null
         * updateUser : 0
         * areaId : 21
         */

        private int ticketId;
        private int userId;
        private String ticketType;
        private int ticketNum;
        private Object createTime;
        private int createUser;
        private Object updateTime;
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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
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
