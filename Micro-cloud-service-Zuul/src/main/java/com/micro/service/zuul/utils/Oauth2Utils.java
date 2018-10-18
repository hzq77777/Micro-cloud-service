package com.micro.service.zuul.utils;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018/1/22.
 */
public class Oauth2Utils   {


    private static String checkTokenUrl ;
    static {
        checkTokenUrl = ApplicationSupport.getParamVal("oauth.check_token");
    }

    public static  OAuth2AccessToken checkTokenInOauth2Client(String tokenValue){
        try {
            RestTemplate restTemplate = new RestTemplate();
            OAuth2AccessToken oAuth2AccessToken = restTemplate.getForObject(checkTokenUrl+"?token="+tokenValue, OAuth2AccessToken.class);
            return oAuth2AccessToken;
        }catch (Exception e){
            return null;
        }
    }


}
