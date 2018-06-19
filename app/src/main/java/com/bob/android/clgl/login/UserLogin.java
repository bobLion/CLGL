package com.bob.android.clgl.login;

/**
 * @package com.bob.android.clgl.entity
 * @fileName UserLogin
 * @Author Bob on 2018/6/18 23:30.
 * @Describe TODO
 */

public class UserLogin {


    /**
     * code : 0
     * data : {"userId":6,"userAccount":"ld1","roleId":"0","passWord":"ld1000","areaId":0,"lastLoginTime":"20180518164912235","createTime":"20180518164912235","createUser":"1","updateTime":"20180518164912235","updateUser":"1"}
     * msg : null
     * count : 0
     * state : 1
     */

    private int code;
    private DataBean data;
    private Object msg;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class DataBean {
        /**
         * userId : 6
         * userAccount : ld1
         * roleId : 0
         * passWord : ld1000
         * areaId : 0
         * lastLoginTime : 20180518164912235
         * createTime : 20180518164912235
         * createUser : 1
         * updateTime : 20180518164912235
         * updateUser : 1
         */

        private int userId;
        private String userAccount;
        private String roleId;
        private String passWord;
        private int areaId;
        private String lastLoginTime;
        private String createTime;
        private String createUser;
        private String updateTime;
        private String updateUser;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }
}
