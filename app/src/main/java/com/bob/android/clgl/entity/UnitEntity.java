package com.bob.android.clgl.entity;

import java.util.List;

/**
 * @package com.bob.android.clgl.entity
 * @fileName UnitEntity
 * @Author Bob on 2018/6/21 14:07.
 * @Describe TODO
 */

public class UnitEntity {

    /**
     * code : 0
     * data : [{"dicDataId":19,"dicDataCode":"company","dicDataName":"一分公司","dicDataValue":"41","sort":1,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":20,"dicDataCode":"company","dicDataName":"二分公司","dicDataValue":"42","sort":2,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":21,"dicDataCode":"company","dicDataName":"三分公司","dicDataValue":"43","sort":3,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":22,"dicDataCode":"company","dicDataName":"四分公司","dicDataValue":"44","sort":4,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":23,"dicDataCode":"company","dicDataName":"上海盘新清洁服务有限公司","dicDataValue":"45","sort":5,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":24,"dicDataCode":"company","dicDataName":"上海骏浩环卫清洁服务有限公司","dicDataValue":"46","sort":6,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":25,"dicDataCode":"company","dicDataName":"上海鲁良清洁服务有限公司","dicDataValue":"47","sort":7,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":26,"dicDataCode":"company","dicDataName":"上海吉立市政工程有限公司","dicDataValue":"48","sort":8,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":27,"dicDataCode":"company","dicDataName":"上海鲁鹏清洁服务有限公司","dicDataValue":"49","sort":9,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null},{"dicDataId":28,"dicDataCode":"company","dicDataName":"上海鲁缔环卫清洁服务有限公司","dicDataValue":"410","sort":10,"enumTypeId":"4","createTime":null,"createUser":null,"updateTime":null,"updateUser":null}]
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
         * dicDataId : 19
         * dicDataCode : company
         * dicDataName : 一分公司
         * dicDataValue : 41
         * sort : 1
         * enumTypeId : 4
         * createTime : null
         * createUser : null
         * updateTime : null
         * updateUser : null
         */

        private int dicDataId;
        private String dicDataCode;
        private String dicDataName;
        private String dicDataValue;
        private int sort;
        private String enumTypeId;
        private Object createTime;
        private Object createUser;
        private Object updateTime;
        private Object updateUser;

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(Object updateUser) {
            this.updateUser = updateUser;
        }
    }
}
