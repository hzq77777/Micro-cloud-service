package com.micro.service.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;


@Component
@Entity
@Table(name="au_device")
public class AuDeviceEntity {
    private  Integer  deviceid ;
    private  String  devicename;
    private   Date createdate;
    private   String  userid;
    private  String  pin;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getDeviceid() {
        return deviceid;
    }
    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }
    @Basic
    @Column(name="device_name",unique = true)
    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    @Basic
    @Column(name="create_date")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name="pin")
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
