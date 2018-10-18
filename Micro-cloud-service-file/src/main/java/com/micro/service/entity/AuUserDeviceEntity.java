package com.micro.service.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/1.
 */

@Component
@Entity
@Table(name = "au_user_device")
public class AuUserDeviceEntity {
    private  Integer  id ;
    private  String  devicename;
    private  String  username;
    private  String  pinname;
    private  String  asts;
    private  String  rsts ;
    private  String  createdate;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Basic
    @Column(name="devicename")
    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }
    @Basic
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Basic
    @Column(name = "pinname")
    public String getPinname() {
        return pinname;
    }

    public void setPinname(String pinname) {
        this.pinname = pinname;
    }
    @Basic
    @Column(name = "access_sts")
    public String getAsts() {
        return asts;
    }

    public void setAsts(String asts) {
        this.asts = asts;
    }
    @Basic
    @Column(name="relate_sts")
    public String getRsts() {
        return rsts;
    }

    public void setRsts(String rsts) {
        this.rsts = rsts;
    }

    @Basic
    @Column(name="create_date")
    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
