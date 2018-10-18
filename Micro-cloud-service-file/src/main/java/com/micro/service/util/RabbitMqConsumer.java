package com.micro.service.util;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class RabbitMqConsumer {
    private static String mqurl = "127.0.0.1";
    private static Integer mqport = 5672;
    private static String mqusername = "test1";
    private static String mqpassword = "123456";

     private  static  void  init(){
         InputStream in = HttpServiceUtil.class.getResourceAsStream("restfuldb.properties");
         Properties p = new Properties();
        try {
            p.load(in) ;
            mqurl=p.getProperty("mqurl");
            mqport=Integer.parseInt(p.getProperty("mqport").trim());
            mqusername=p.getProperty("mqusername");
            mqpassword=p.getProperty("mqpassword") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
    public static void collectmessage() throws IOException, TimeoutException, InterruptedException {
      //  init();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(mqurl);
        connectionFactory.setPort(mqport);
        connectionFactory.setUsername(mqusername);
        connectionFactory.setPassword(mqpassword);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //声明关注的队列
        AMQP.Queue.DeclareOk test = channel.queueDeclare("testmq", true , false, false, null);
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);//QueueingConsumer extends DefaultConsumer
        channel.basicConsume("testmq",true,queueingConsumer);
        while(true){//一直执行，获取Delivery传递对象
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
//            System.out.println(new String(delivery.getBody(),"UTF-8"));
            String  message=new String(delivery.getBody(),"UTF-8");
            JSONObject json = JSONObject.parseObject(message);
            String mkey=json.getString("key");
            String  mvalue=json.getString("value");
            System.out.println("key: "+ mkey +", value: "+ mvalue );
           if("add".equals(mkey)){
               jdbcoperation.init();
               jdbcoperation.save(mvalue);

           }else if("del".equals(mkey)){
               jdbcoperation.init();
               jdbcoperation.del(mvalue);
           }else{
               continue;
           }
        }
     }

     public static  void main(String args[]){
         try {
             collectmessage();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (TimeoutException e) {
             e.printStackTrace();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
}
