package com.bob.android.clgl.entity;

/**
 * @package com.bob.android.clgl.entity
 * @fileName VehicleEntity
 * @Author Bob on 2018/5/16 14:59.
 * @Describe TODO
 */

public class VehicleEntity {

    private String invoiceId;//发票主键
    private String areaId;//区域id
    private String licenseNum;//车牌号
    private String netWeight;//净量
    private String roughWeight;//毛重
    private String unit;//单位
    private String invoiceNum;
    private  String nowTime;

    public String getNowTime() {
        return nowTime;
    }

    public VehicleEntity setNowTime(String nowTime) {
        this.nowTime = nowTime;
        return this;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public VehicleEntity setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public VehicleEntity setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public String getAreaId() {
        return areaId;
    }

    public VehicleEntity setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public VehicleEntity setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
        return this;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public VehicleEntity setNetWeight(String netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public String getRoughWeight() {
        return roughWeight;
    }

    public VehicleEntity setRoughWeight(String roughWeight) {
        this.roughWeight = roughWeight;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public VehicleEntity setUnit(String unit) {
        this.unit = unit;
        return this;
    }

}
