package com.micro.service.util;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class jdbcoperation {

    static Connection conn = null;
    private static String dbname = "root";
    private static String dbpassword = "123456";
    private static String dburl = "jdbc:mysql://localhost:3306/certdb";
    private static String driverName = "com.mysql.jdbc.Driver";

    public static void init() {
        InputStream in = HttpServiceUtil.class.getResourceAsStream("restfuldb.properties");
        Properties p = new Properties();
//        try {
//            p.load(in);
//            dburl = p.getProperty("dburl");
//            dbname = p.getProperty("dbusername");
//            dbpassword = p.getProperty("dbpassword");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    public static void save(String userid)  {
        PreparedStatement pstq, psti;
        ResultSet rSet;
        String sqlString = "select * from au_user where user_name=? ";
        String sql = "insert into au_user(create_date, user_name, sts) values (?, ?, ?)";
        try {
         pstq = conn.prepareStatement(sqlString);
        psti = conn.prepareStatement(sql);
        pstq.setString(1, userid);
        rSet = pstq.executeQuery();
        if (rSet.next()) {
            return;
        }
        java.util.Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowdata = formatter.format(date);
        psti.setString(1, nowdata);
        psti.setString(2, userid);
        psti.setString(3, "P");
        psti.executeUpdate();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
            try {
             if (conn != null && !conn.isClosed())
                    conn.close();
                    conn = null;;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  static void del(String userid)  {
        PreparedStatement pstq, pstd,psto ;
        ResultSet rSet;
        String sqlString = "select * from au_user where user_name=? ";
        String sql = "delete from au_user where user_name=?";
        String osql = "delete from au_user_device where username=?";

        try {
            conn.setAutoCommit(false);
        pstq = conn.prepareStatement(sqlString);
        pstd = conn.prepareStatement(sql);
        psto=conn.prepareStatement(osql);
        pstq.setString(1, userid);
        rSet = pstq.executeQuery();
        if (rSet.next()) {
            pstd.setString(1, userid);
            pstd.executeUpdate();
            psto.setString(1,userid);
            psto.executeUpdate();
        }
            conn.commit();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed())
                    conn.close();
                conn = null;;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 }



