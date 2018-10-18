package com.micro.service.zuul.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/5/2.
 */
@Entity
@Table(name = "user_roles")
public class UserRolesEntity {
    @Id
    @Column(name = "id")
     private Long id;

//    @ManyToMany
//    @JoinColumn(name = "ur_user_id")
//    private   String  userid;
//
//
//    @ManyToMany
//    @JoinColumn(name = "ur_role_id")
//    private  String  roleid;

}
