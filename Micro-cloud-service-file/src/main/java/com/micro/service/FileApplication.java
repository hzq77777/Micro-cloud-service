package com.micro.service;

import com.micro.service.util.HttpServiceUtil;
import com.micro.service.util.RabbitMqConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2018/2/1.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FileApplication {

    public static void main(String args[]) {

        SpringApplication.run(FileApplication.class, args);

        try {
            HttpServiceUtil.restful() ;
            RabbitMqConsumer.collectmessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch(Exception e ){
            e.printStackTrace();
        }
    }


}
