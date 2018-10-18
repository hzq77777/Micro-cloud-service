package com.micro.service.entity;


import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;


/**
 * Created by Administrator on 2018/2/6.
 */
@Component
@Entity
@Table(name="au_user")
public class AuUserEntity {

    private Integer userid;
    private String username;
    private Date  createdate;
    private  String  asts;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)

    public Integer getUserid() { return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "user_name", unique= true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "create_date")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "asts")
    public String getAsts() {
        return asts;
    }

    public void setAsts(String asts) {
        this.asts = asts;
    }
}
