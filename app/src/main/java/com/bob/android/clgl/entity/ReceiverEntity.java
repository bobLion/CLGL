package com.bob.android.clgl.entity;

import java.io.Serializable;

/**
 * @package com.bob.android.clgl.entity
 * @fileName DriverEntity
 * @Author Bob on 2018/6/18 22:50.
 * @Describe TODO
 */

public class ReceiverEntity implements Serializable{

    private static final long serialVersionUID = 1L;


    public ReceiverEntity(){}
    /**
     * code : 0
     * data : {"receiveId":2,"receiveName":"战神","receivePhone":"15888552252","userId":8,"state":"1","createTime":"20180619232141323","createUser":0,"updateTime":null,"updateUser":0}
     * msg : 保存接收人信息
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

    public static class DataBean implements Serializable{
        /**
         * receiveId : 2
         * receiveName : 战神
         * receivePhone : 15888552252
         * userId : 8
         * state : 1
         * createTime : 20180619232141323
         * createUser : 0
         * updateTime : null
         * updateUser : 0
         */

        private int receiveId;
        private String receiveName;
        private String receivePhone;
        private int userId;
        private String state;
        private String createTime;
        private int createUser;
        private Object updateTime;
        private int updateUser;

        public DataBean(){}
        public int getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(int receiveId) {
            this.receiveId = receiveId;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceivePhone() {
            return receivePhone;
        }

        public void setReceivePhone(String receivePhone) {
            this.receivePhone = receivePhone;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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
    }
}
