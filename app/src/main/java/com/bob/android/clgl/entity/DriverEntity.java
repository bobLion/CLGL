package com.bob.android.clgl.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @package com.bob.android.clgl.entity
 * @fileName DriverEntity
 * @Author Bob on 2018/6/18 22:50.
 * @Describe TODO
 */

@Entity
public class DriverEntity {

    @Id
    private Long id;

    private String driverNum;

    private String driverName;

    @Generated(hash = 1949439201)
    public DriverEntity(Long id, String driverNum, String driverName) {
        this.id = id;
        this.driverNum = driverNum;
        this.driverName = driverName;
    }

    @Generated(hash = 1213357986)
    public DriverEntity() {
    }

    public String getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(String driverNum) {
        this.driverNum = driverNum;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
