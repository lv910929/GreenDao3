package com.lv.greendao3.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lv on 2017/2/28.
 */

@Entity
public class Phone {

    public final static int CMCC = 1;//移动
    public final static int CUCC = 2;//联通
    public final static int CTCC = 3;//电信

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "PHONE_NUMBER")
    private String phoneNumber;
    @Property(nameInDb = "PHONE_TYPE")
    private int phoneType;

    private Long userId;

    @Generated(hash = 1254733412)
    public Phone(Long id, String phoneNumber, int phoneType, Long userId) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.userId = userId;
    }

    @Generated(hash = 429398894)
    public Phone() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
