package com.micro.service.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/30.
 */
@Component
@Entity
@Table(name="au_pin")
public class AuPinEntiry {

    private Integer  Pinid;
    private String pin;
    private Date createdate;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer  getPinid() {
        return Pinid;
    }

    public void setPinid(Integer pinid) {
        Pinid = pinid;
    }
    @Basic
    @Column(name="pin")
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    @Basic
    @Column(name="create_date")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
