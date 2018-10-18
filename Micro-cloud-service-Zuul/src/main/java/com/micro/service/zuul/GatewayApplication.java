package com.micro.service.zuul;

import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.commons.httpclient.DefaultApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/24.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//@EnableOAuth2Sso
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public ApacheHttpClientConnectionManagerFactory connManFactory() {
        return new DefaultApacheHttpClientConnectionManagerFactory() {

            @Value("${server.ssl.key-store}")
            private String trustStorePath;
            @Value("${server.ssl.key-store-password:" +
                    ".}")
            private String trustStorePassword;
            @Value("${server.ssl.keyStoreType}")
            private String trustStoreType;
            @Value("${server.ssl.keyAlias}")
            private String trustAlias;
            @Override
            public HttpClientConnectionManager newConnectionManager(boolean disableSslValidation, int maxTotalConnections, int maxConnectionsPerRoute,
                                                                    long timeToLive, TimeUnit timeUnit, RegistryBuilder registryBuilder) {
                final RegistryBuilder<ConnectionSocketFactory> registryBuilderToUse;

                KeyStore trustStore = null;
                SSLConnectionSocketFactory systemSocketFactory = null;
                try {
                    trustStore = KeyStore.getInstance(trustStoreType);
                    trustStore.load(new ClassPathResource(trustStorePath).getInputStream(), trustStorePassword.toCharArray());
                    trustStore.containsAlias(trustAlias);
                    systemSocketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                            .build(), new NoopHostnameVerifier());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (registryBuilder == null) {
                    registryBuilderToUse =
                            RegistryBuilder.<ConnectionSocketFactory>create().register(HTTP_SCHEME, PlainConnectionSocketFactory.INSTANCE).register(
                                    ApacheHttpClientConnectionManagerFactory.HTTPS_SCHEME, systemSocketFactory);
                } else {
                    registryBuilderToUse = registryBuilder;
                }
                return super.newConnectionManager(disableSslValidation, maxTotalConnections, maxConnectionsPerRoute, timeToLive, timeUnit,
                        registryBuilderToUse);
            }
        };
    }

}
