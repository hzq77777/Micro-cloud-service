package com.micro.service.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micro.service.service.FileService;
import com.micro.service.util.ApplicationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

@Controller
public class FileUploadController {

    private static Logger log = LoggerFactory.getLogger(FileUploadController.class);
   @Autowired
   private  FileService fileService;

    @RequestMapping(value = "/uploads", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
    @ResponseBody
    public  String handleFileUploads(@RequestParam(value = "file", required = true) MultipartFile file) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        log.info("upload file : "+file.getName());
        return fileService.uploadfiles(file);
    }


    @RequestMapping(value = "/j_spring_security_check")
    public  String login(HttpServletResponse rsp) {
         rsp.addHeader("Access-Control-Allow-Origin", "*");
         rsp.setHeader("Content-Type", "application/json;charset=UTF-8");
         return  "signin";
    }

    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(HttpServletResponse res) {
        String fileName = "test_aaaa.txt";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("E://"
                    + fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    @ResponseBody
    public String  listmessages(HttpServletResponse res) {
        String  retult=fileService.Listfiles();
        return retult ;
    }

    @RequestMapping(value = "/messages/r/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String  updatemessagesbyr(@PathVariable String  id) {
        String  retult=fileService.updatefilersts(id,"rsts_A");
        return  retult ;
    }
    @RequestMapping(value = "/messages/a/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String  updatemessagesbya(@PathVariable String  id) {
        String  retult=fileService.updatefilersts(id,"asts_A");
        return  retult ;
    }
    @RequestMapping(value = "/messages/rp/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String  updatemessagesbyrp(@PathVariable String  id) {
        String  retult=fileService.updatefilersts(id,"rsts_P");
        return  retult ;
    }
    @RequestMapping(value = "/messages/ap/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String  updatemessagesbyap(@PathVariable String  id) {
        String  retult=fileService.updatefilersts(id,"asts_P");
        return  retult ;
    }

    @RequestMapping(value = "/messages/userid/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String  searchByUserid(@PathVariable String  id) {
        String  retult=fileService.findmessagebyid(id);
        return  retult ;
    }

    @RequestMapping(value = "/messages/device/{mac}", method = RequestMethod.GET)
    @ResponseBody
    public String  searchByDeviceMac(@PathVariable String  mac) {
        String  retult=fileService.findmessagebymac(mac);
        return  retult ;
    }

    //获取整个mac
    @RequestMapping(value = "/messages/mac/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String  selectmac(@PathVariable String  id) {
        String  retult=fileService.selectmac(id);
        return  retult ;
    }

    //绑定单个mac
    @RequestMapping(value = "/mac/a/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String  bangdingmac(@PathVariable String  id) {
        String endid=id.replace("%7B","{").replace("%22" ,"\"").replace("%7D","}");
        System.out.println(endid);
        JSONObject ob = JSON.parseObject(endid);
        String  userid=ob.getString("userId") ;
        String  mac=ob.getString("mac");
        String  retun=fileService.bangdingmac(userid,mac);
        System.out.println(retun);
        return retun;
    }


}