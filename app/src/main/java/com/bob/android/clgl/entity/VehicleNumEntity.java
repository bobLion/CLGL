package com.bob.android.clgl.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName VehicleNumEntity
 * @Author Bob on 2018/6/21 14:49.
 * @Describe TODO
 */

public class VehicleNumEntity {


    /**
     * code : 0
     * data : [{"carAreaId":1,"carno":"沪D49759\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":19,"carno":"沪BS4408\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":20,"carno":"沪DC2811\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":21,"carno":"沪DF7417\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":22,"carno":"沪DL4438\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":33,"carno":"沪DL2428\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":34,"carno":"沪DC2908\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":35,"carno":"沪D76260\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":36,"carno":"沪BG5031\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":41,"carno":"沪DR0190       \r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":44,"carno":"沪EC2073\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":48,"carno":"沪EL1975\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0},{"carAreaId":50,"carno":"沪DT7357\r\n","areaId":"211","createTime":null,"createUser":0,"updateTime":null,"updateUser":0}]
     * msg : null
     * count : 0
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

    public static class DataBean implements Serializable{
        /**
         * carAreaId : 1
         * carno : 沪D49759

         * areaId : 211
         * createTime : null
         * createUser : 0
         * updateTime : null
         * updateUser : 0
         */

        private int carAreaId;
        private String carno;
        private String areaId;
        private Object createTime;
        private int createUser;
        private Object updateTime;
        private int updateUser;

        public int getCarAreaId() {
            return carAreaId;
        }

        public void setCarAreaId(int carAreaId) {
            this.carAreaId = carAreaId;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
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
    }
}
