package com.bob.android.clgl.entity;

/**
 * @package com.bob.android.clgl.entity
 * @fileName CreatePswdEntity
 * @Author Bob on 2018/6/19 0:32.
 * @Describe TODO
 */

public class CreatePswdEntity {


    /**
     * code : 0
     * data : {"pwdValue":"146784"}
     * msg : 生成密码
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
         * pwdValue : 146784
         */

        private String pwdValue;

        public String getPwdValue() {
            return pwdValue;
        }

        public void setPwdValue(String pwdValue) {
            this.pwdValue = pwdValue;
        }
    }
}
