package com.micro.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30.
 */
public class testt {

    public  static  void  main(String args[]){
        func3();
    }

    public static void func3(){
        int  nums[]=new int[10];
         for(int  i=0;i<nums.length;i++){
             System.out.println(nums[i]);
         }
    }

    public static  void  func2(){
        String  jsons="{\"userId\":\"admin\",\"mac\":\"000C297D82E9\"}" ;
        JSONObject  jsonObject = JSONObject.parseObject(jsons);
        System.out.println(jsonObject.get("userId"));
    }
    public  static  void  func1(){
        String  ttt[]=new String []{"aa","bbb"};
        String jsonString2 = JSON.toJSONString(ttt);
        System.out.println("jsonString2:" + jsonString2);
        List<String>  results= JSONArray.parseArray(jsonString2, String.class);
        for(String st:results){
            System.out.println(st);
        }
    }

}
