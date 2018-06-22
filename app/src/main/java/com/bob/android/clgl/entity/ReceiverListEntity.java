package com.bob.android.clgl.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName ReceiverListEntity
 * @Author Bob on 2018/6/19 23:43.
 * @Describe TODO
 */

public class ReceiverListEntity {

    /**
     * code : 0
     * data : [{"receiveId":2,"receiveName":"战神","receivePhone":"15888552252","userId":8,"state":null,"createTime":"20180619232141323","createUser":0,"updateTime":null,"updateUser":0}]
     * msg : 查询所属发票接收人
     * count : 0
     * state : 1
     */

    private int code;
    private String msg;
    private int count;
    private String state;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * receiveId : 2
         * receiveName : 战神
         * receivePhone : 15888552252
         * userId : 8
         * state : null
         * createTime : 20180619232141323
         * createUser : 0
         * updateTime : null
         * updateUser : 0
         */

        private int receiveId;
        private String receiveName;
        private String receivePhone;
        private int userId;
        private Object state;
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

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
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
