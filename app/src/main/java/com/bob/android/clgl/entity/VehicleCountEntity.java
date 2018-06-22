package com.bob.android.clgl.entity;

import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName CheckTicetEntity
 * @Author Bob on 2018/6/20 17:22.
 * @Describe TODO
 */

public class VehicleCountEntity {


    /**
     * code : 0
     * data : [{"consumeId":0,"pwdId":0,"areaId":0,"ticketType":null,"carNo":null,"netWeight":null,"roughWeight":null,"unit":null,"createTime":null,"createUser":0,"updateTime":null,"updateUser":0,"carTims":0,"weights":"0","nowTime":null,"dicDataName":"新江湾","dicDataValue":"23"}]
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
         * consumeId : 0
         * pwdId : 0
         * areaId : 0
         * ticketType : null
         * carNo : null
         * netWeight : null
         * roughWeight : null
         * unit : null
         * createTime : null
         * createUser : 0
         * updateTime : null
         * updateUser : 0
         * carTims : 0
         * weights : 0
         * nowTime : null
         * dicDataName : 新江湾
         * dicDataValue : 23
         */

        private int consumeId;
        private int pwdId;
        private int areaId;
        private Object ticketType;
        private Object carNo;
        private Object netWeight;
        private Object roughWeight;
        private Object unit;
        private Object createTime;
        private int createUser;
        private Object updateTime;
        private int updateUser;
        private int carTims;
        private String weights;
        private Object nowTime;
        private String dicDataName;
        private String dicDataValue;

        public int getConsumeId() {
            return consumeId;
        }

        public void setConsumeId(int consumeId) {
            this.consumeId = consumeId;
        }

        public int getPwdId() {
            return pwdId;
        }

        public void setPwdId(int pwdId) {
            this.pwdId = pwdId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public Object getTicketType() {
            return ticketType;
        }

        public void setTicketType(Object ticketType) {
            this.ticketType = ticketType;
        }

        public Object getCarNo() {
            return carNo;
        }

        public void setCarNo(Object carNo) {
            this.carNo = carNo;
        }

        public Object getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(Object netWeight) {
            this.netWeight = netWeight;
        }

        public Object getRoughWeight() {
            return roughWeight;
        }

        public void setRoughWeight(Object roughWeight) {
            this.roughWeight = roughWeight;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
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

        public int getCarTims() {
            return carTims;
        }

        public void setCarTims(int carTims) {
            this.carTims = carTims;
        }

        public String getWeights() {
            return weights;
        }

        public void setWeights(String weights) {
            this.weights = weights;
        }

        public Object getNowTime() {
            return nowTime;
        }

        public void setNowTime(Object nowTime) {
            this.nowTime = nowTime;
        }

        public String getDicDataName() {
            return dicDataName;
        }

        public void setDicDataName(String dicDataName) {
            this.dicDataName = dicDataName;
        }

        public String getDicDataValue() {
            return dicDataValue;
        }

        public void setDicDataValue(String dicDataValue) {
            this.dicDataValue = dicDataValue;
        }
    }
}
