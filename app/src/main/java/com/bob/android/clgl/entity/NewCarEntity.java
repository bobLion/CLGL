package com.bob.android.clgl.entity;

/**
 * @package com.bob.android.clgl.entity
 * @fileName NewCarEntity
 * @Author Bob on 2018/5/27 9:27.
 * @Describe TODO
 */

public class NewCarEntity {


    /**
     * jsonStr : {"车号":"沪EA0723","序号":"1563","areaName":"平凉","净重":"3360.000","invoiceType":"黄色","毛重":"7660.000"}
     * state : SUCCEED
     */

    private JsonStrBean jsonStr;
    private String state;
    private String msg;

    public JsonStrBean getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(JsonStrBean jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class JsonStrBean {
        /**
         * 车号 : 沪EA0723
         * 序号 : 1563
         * areaName : 平凉
         * 净重 : 3360.000
         * invoiceType : 黄色
         * 毛重 : 7660.000
         */

        private String 车号;
        private String 序号;
        private String areaName;
        private String 净重;
        private String invoiceType;
        private String 毛重;

        public String get车号() {
            return 车号;
        }

        public void set车号(String 车号) {
            this.车号 = 车号;
        }

        public String get序号() {
            return 序号;
        }

        public void set序号(String 序号) {
            this.序号 = 序号;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String get净重() {
            return 净重;
        }

        public void set净重(String 净重) {
            this.净重 = 净重;
        }

        public String getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String get毛重() {
            return 毛重;
        }

        public void set毛重(String 毛重) {
            this.毛重 = 毛重;
        }
    }


}
