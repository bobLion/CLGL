package com.bob.android.clgl.entity;

import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName AreaEntity
 * @Author Bob on 2018/6/20 14:47.
 * @Describe TODO
 */

public class AreaEntity {


    /**
     * code : 0
     * data : [{"dicDataId":5,"dicDataCode":"areaType","dicDataName":"平凉","dicDataValue":"21","sort":1,"enumTypeId":"2","createTime":"20180525002312423","createUser":"sys","updateTime":"20180525002312423","updateUser":"sys"},{"dicDataId":7,"dicDataCode":"areaType","dicDataName":"江浦","dicDataValue":"22","sort":2,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":8,"dicDataCode":"areaType","dicDataName":"新江湾","dicDataValue":"23","sort":2,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":9,"dicDataCode":"areaType","dicDataName":"延吉","dicDataValue":"24","sort":2,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":10,"dicDataCode":"areaType","dicDataName":"定海","dicDataValue":"25","sort":2,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":6,"dicDataCode":"areaType","dicDataName":"五街","dicDataValue":"26","sort":2,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":11,"dicDataCode":"areaType","dicDataName":"大桥","dicDataValue":"27","sort":7,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":12,"dicDataCode":"areaType","dicDataName":"四平","dicDataValue":"28","sort":8,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":13,"dicDataCode":"areaType","dicDataName":"控江","dicDataValue":"29","sort":9,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":14,"dicDataCode":"areaType","dicDataName":"五镇","dicDataValue":"210","sort":10,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":15,"dicDataCode":"areaType","dicDataName":"长白","dicDataValue":"211","sort":11,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":16,"dicDataCode":"areaType","dicDataName":"殷行","dicDataValue":"212","sort":12,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":29,"dicDataCode":"areaType","dicDataName":"五角场","dicDataValue":"213","sort":13,"enumTypeId":"2","createTime":null,"createUser":null,"updateTime":null,"updateUser":null}]
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

    public static class DataBean {
        /**
         * dicDataId : 5
         * dicDataCode : areaType
         * dicDataName : 平凉
         * dicDataValue : 21
         * sort : 1
         * enumTypeId : 2
         * createTime : 20180525002312423
         * createUser : sys
         * updateTime : 20180525002312423
         * updateUser : sys
         */

        private int dicDataId;
        private String dicDataCode;
        private String dicDataName;
        private String dicDataValue;
        private int sort;
        private String enumTypeId;
        private String createTime;
        private String createUser;
        private String updateTime;
        private String updateUser;

        public int getDicDataId() {
            return dicDataId;
        }

        public void setDicDataId(int dicDataId) {
            this.dicDataId = dicDataId;
        }

        public String getDicDataCode() {
            return dicDataCode;
        }

        public void setDicDataCode(String dicDataCode) {
            this.dicDataCode = dicDataCode;
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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getEnumTypeId() {
            return enumTypeId;
        }

        public void setEnumTypeId(String enumTypeId) {
            this.enumTypeId = enumTypeId;
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
