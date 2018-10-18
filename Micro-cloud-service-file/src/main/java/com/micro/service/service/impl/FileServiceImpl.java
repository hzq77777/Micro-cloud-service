package com.micro.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.micro.service.entity.AuDeviceEntity;
import com.micro.service.entity.AuPinEntiry;
import com.micro.service.entity.AuUserDeviceEntity;
import com.micro.service.entity.AuUserEntity;
import com.micro.service.repository.AuDeviceRepository;
import com.micro.service.repository.AuPinRepository;
import com.micro.service.repository.AuUserDeviceRepository;
import com.micro.service.repository.AuUserRepository;
import com.micro.service.service.FileService;
import com.micro.service.util.AesPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/2/5.
 */
@Service
public class FileServiceImpl implements FileService {
    private static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);


    //文件保存的路径
    @Value("${file.Upload.path}")
    private String path;
    //是否对文件进行保存，可根据配置文件进行控制配置
    @Value("${file.issave}")
    private boolean issave;

    @Autowired
    private AuDeviceRepository auDeviceRepository;
    @Autowired
    private AuUserRepository auUserRepository;
    @Autowired
    private AuUserDeviceRepository auUserDeviceRepository;

     @Autowired
    private AuPinRepository  auPinRepository ;

    @Autowired
    private AuDeviceEntity device;
    @Autowired
    private AuUserEntity users;
    @Autowired
    private   AuPinEntiry  pin ;
    @Autowired
    private AuUserDeviceEntity userdevice;


    /**
     * @param file
     * @return
     * @throws IOException 获取客户端上传文件，根据配置确定是否对文件进行保存，
     *                     对上传文件使用AES算法进行解密，提取里面的JSON字段（暂时为用户信息，设备信息, pin 码信息）
     *                     并返回该字段
     */
    @Override
    public String uploadfiles(MultipartFile file) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, Object> remap = new HashMap<String, Object>();
        java.sql.Date nowdate = new Date(System.currentTimeMillis());
        File fileToSave = null;
        byte[] bytes = file.getBytes();
        //如果需要将上传文件临时保存到本地磁盘中。
        UUID uuid = UUID.randomUUID();
        if (issave) {
            fileToSave = new File(path + File.separatorChar + uuid + "$" + file.getOriginalFilename());
            FileCopyUtils.copy(bytes, fileToSave);
        }
        InputStream  inputStream=file.getInputStream();
        byte[] byt = null;
        byt = new byte[inputStream.available()];
        inputStream.read(byt);
        String str = new String(byt);
        String  message=null;
        try {
            message=  AesPassword.Decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("上传的内容是： " + message);
        Map<String, String> jmessage = JSON.parseObject(message, Map.class);
        device.setDevicename(jmessage.get("deviceid"));
        pin.setPin(jmessage.get("pin"));
        device.setCreatedate(nowdate);
        device.setPin(jmessage.get("pin"));
        pin.setCreatedate(nowdate);
        SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss") ;
        if ( null != auDeviceRepository.findBydevicename(device.getDevicename())) {
            remap.put("ZXSJJRAQ_ZDGL_ZDFW_DRU_RES", "false");
        } else {
            auDeviceRepository.save(device);
            auPinRepository.save(pin);
            remap.put("ZXSJJRAQ_ZDGL_ZDFW_DRU_RES", "true");
        }
        return JSON.toJSONString(remap);
    }

    @Override
    public String updatefilersts(String name, String sts) {
        AuUserDeviceEntity mvo = auUserDeviceRepository.findByusername(name);
        AuUserEntity  umvo=auUserRepository.findByusername(name);
        switch (sts) {
            case "rsts_A":
                mvo.setRsts("A");
                auUserDeviceRepository.saveAndFlush(mvo);
                break;
            case "rsts_P":
               // mvo.setRsts("P");
                auUserDeviceRepository.delete(mvo);
                break;
            case "asts_A":
                umvo.setAsts("A");
                auUserRepository.saveAndFlush(umvo);
                break;
            case "asts_P":
                umvo.setAsts("P");
                auUserRepository.saveAndFlush(umvo);
                break;
            default:
                return "1";
        }
//        AuUserDeviceEntity mv = findbymessgaeid(mvo.getUsername());
//        if (mv != null) {
//            List<AuUserDeviceEntity> resutl = new ArrayList<>();
//
//            resutl.add(mv);
//            return JSONArray.toJSONString(resutl);
//        }
        return Listfiles();
    }


    @Override
    public String Listfiles() {
        List<AuUserDeviceEntity> lcert = auUserDeviceRepository.findAll();
        List<AuUserEntity>  luser= auUserRepository.findAll();
        List<AuUserDeviceEntity> retu=new ArrayList<AuUserDeviceEntity>();
        for(AuUserEntity user: luser){
            AuUserDeviceEntity  entity=new AuUserDeviceEntity();
            entity.setUsername(user.getUsername());
            entity.setAsts(user.getAsts());
            retu.add(entity);
        }
        for(AuUserDeviceEntity uretun: retu){
            for(AuUserDeviceEntity cert:lcert){
                if(uretun.getUsername().equals(cert.getUsername())){
                    uretun.setCreatedate(cert.getCreatedate());
                    uretun.setDevicename(cert.getDevicename());
                    uretun.setPinname(cert.getPinname());
                    uretun.setRsts(cert.getRsts());
                }
            }
        }
        return JSONArray.toJSONString(retu);
    }

    @Override
    public String findmessagebyid(String username) {
        List<AuUserDeviceEntity>  resutl=new ArrayList<AuUserDeviceEntity>();
        AuUserDeviceEntity Entity = auUserDeviceRepository.findByusername(username);
        if(null==Entity){
            AuUserEntity  users=auUserRepository.findByusername(username);
            if(users!=null) {
                Entity=new  AuUserDeviceEntity();
                Entity.setUsername(users.getUsername());
                Entity.setAsts(users.getAsts());
                Entity.setRsts("P");
                SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
                Entity.setCreatedate(simpleDateFormat.format(users.getCreatedate()));
            }
        }
        resutl.add(Entity) ;
        return JSONArray.toJSONString(resutl);
    }

    @Override
    public String findmessagebymac(String devicename) {
        List<AuUserDeviceEntity>  resutl=new ArrayList<AuUserDeviceEntity>();
        AuUserDeviceEntity Entity = auUserDeviceRepository.findBydevicename(devicename);
        resutl.add(Entity) ;
        return JSONArray.toJSONString(resutl);
    }

    private AuUserDeviceEntity findbymessgaeid(String username) {
        AuUserDeviceEntity entity = auUserDeviceRepository.findByusername(username) ;
        return  entity ;
    }


    public String selectmac(String username) {
        List<AuDeviceEntity> ldev = auDeviceRepository.findAll();
        if(ldev!=null){
            for(AuDeviceEntity l:ldev){
                l.setUserid(username);
            }
        }
        return JSONArray.toJSONString(ldev);
    }

    public  String  bangdingmac(String  userid, String  mac){
        Map<String, Object> remap = new HashMap<String, Object>();
        device.setDevicename(mac);
        AuDeviceEntity entity=  auDeviceRepository.findBydevicename(mac);
        userdevice.setDevicename(mac);
        Boolean  isr =auUserDeviceRepository.findBydevicename(mac)==null
                  &&auUserDeviceRepository.findByusername(userid)==null ;
        SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss") ;
         java.util.Date date=new java.util.Date();
         String  newdate=format.format(date);
        if(isr&&entity!=null) {
            userdevice.setRsts("A");
            userdevice.setUsername(userid);
            userdevice.setDevicename(mac);
            userdevice.setPinname(entity.getPin());
            userdevice.setCreatedate(newdate);
            auUserDeviceRepository.save(userdevice);
            return "1";
        }else {
         return "0";
        }
    }
}
