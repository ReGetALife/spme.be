package com.example.demo;

import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("测试开始");
        String zosJsession="";
        String zosToken="";
        //禁用ssl证书校验
        CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        //访问zosmf获取jsessionid
        String urlOverHttps = "https://10.60.43.8:8800/zosmf/";
        HttpHeaders httpHeaders = new RestTemplate(requestFactory).headForHeaders(urlOverHttps);
        List<String> setCookie = httpHeaders.get("Set-Cookie");
        if(setCookie!=null){
            zosJsession=setCookie.get(0).split(";")[0];
            System.out.println(zosJsession);
        }
        else {
            System.out.println("header中没有获取到set-cookie信息");
        }
        //访问zosmf获取token
        urlOverHttps = "https://10.60.43.8:8800/zosmf/LoginServlet";
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie",zosJsession);
        headers.add("Referer","https://10.60.43.8:8800/zosmf/");//欺骗服务器这不是csrf这不是csrf这不是csrf
        //添加表单数据
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("requestType", "Login");
        map.add("username", "st021");
        map.add("password", "hello");
        //request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(urlOverHttps,request,String.class);
        zosToken=response.toString().split("Set-Cookie:\"|; Path")[1];
        System.out.println(zosToken);
    }

}
