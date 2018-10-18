package com.micro.service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
/**
 * Created by Administrator on 2018/2/5.
 */
public interface FileService {
    /**
     * @param file
     * @return
     * @throws IOException
     * 获取客户端上传文件，根据配置确定是否对文件进行保存，
     * 对上传文件使用AES算法进行解密，(暂时为用户信息，设备信息）
     * 并返回该字段
     */

    public String uploadfiles(MultipartFile file) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;


    public  String  updatefilersts(String id, String sts) ;

    public  String   Listfiles();

    public  String   findmessagebyid(String id);

    public  String  findmessagebymac(String mac) ;

    public  String   selectmac(String id);

    public  String  bangdingmac(String  userid, String  mac);



    }
