package com.micro.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.SimpleFormatter;

public class HttpServiceUtil {
    static Connection conn = null;
    private static String dbname = "root";
    private static String dbpassword = "123456";
    private static String dburl = "jdbc:mysql://localhost:3306/certdb";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String restful = "";

    public static void restful() {
        InputStream in = HttpServiceUtil.class.getResourceAsStream("restfuldb.properties");
        Properties p = new Properties();
        try {
             p.load(in) ;
            dburl=p.getProperty("dburl");
            dbname=p.getProperty("dbusername");
            dbpassword=p.getProperty("dbpassword");
            restful=p.getProperty("restfulurl") ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        String  ttt[]=new String []{"aa","cccc","dddd","bbb"};
        String jsonString2 = JSON.toJSONString(ttt);
        List<String>  results= JSONArray.parseArray(jsonString2, String.class);
 //       List<String> results = JSONArray.parseArray(insureResponseBlockGet(restful), String.class);
        try {
            selectinsert(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            // 加载驱动
            Class.forName(driverName);
            conn = DriverManager.getConnection(dburl, dbname, dbpassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String insureResponseBlockGet(String url) {
        PrintWriter out = null;
        String result = "";
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer strBuffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(300000);
            // 传输数据为json，如果为其他格式可以进行修改
            conn.setRequestProperty("Content-Type", "application/json");
            is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            System.out.println("发送 GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void selectinsert(List<String> list) throws SQLException {
        PreparedStatement pst;
        ResultSet rSet;
        String sqlString = "select * from au_user";
        pst = conn.prepareStatement(sqlString);
        rSet = pst.executeQuery();
        while (rSet.next()) {
            list.remove(rSet.getString("user_name"));
        }
        String sql = "insert into au_user(create_date, user_name, sts) values (?, ?, ?)";
        pst = conn.prepareStatement(sql);
        java.util.Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdata = formatter.format(date);
        for (String st : list) {
            pst.setString(1, nowdata);
            pst.setString(2, st);
            pst.setString(3, "P");
            pst.executeUpdate();
        }
    }

    public static void main(String args[]) {
        restful();
    }
}
