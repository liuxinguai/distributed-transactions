package com.github.liuxg.example.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        final InputStream stream = Main.class.getClassLoader().getResourceAsStream("nacos-config.properties");
        final Properties properties = new Properties();
        properties.load(stream);
        System.out.println(properties);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            Object element = keys.nextElement();
            System.out.println("set "+element+" = "+properties.getProperty(element.toString()));
            HttpPost post = new HttpPost("http://127.0.0.1:8848/nacos/v1/cs/configs?dataId="+element+"&group=SEATA_GROUP&content="+properties.getProperty(element.toString()));
            CloseableHttpResponse response = httpClient.execute(post);
            response.close();
//            System.out.println(element+"="+properties.getProperty(element.toString()));
        }
        System.out.println("..................");
//        httpClient.execute()
    }
}
