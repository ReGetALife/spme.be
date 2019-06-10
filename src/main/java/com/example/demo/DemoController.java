package com.example.demo;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class DemoController {

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> account, HttpSession session)
            throws ClientProtocolException, IOException {
        //获取session
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            System.out.println("session未保存，从zosmf获取token并保存至session。");

            //获取zomsmf地址
            ZOSMF_Address=account.get("address");
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //访问zosmf获取jsessionid
            String zosmfUrlOverHttps = "https://"+ZOSMF_Address.toString()+"/zosmf/";
            HttpHeaders httpHeaders = new RestTemplate(requestFactory).headForHeaders(zosmfUrlOverHttps);
            List<String> setCookie = httpHeaders.get("Set-Cookie");
            if (setCookie != null) {
                ZOSMF_JSESSIONID = setCookie.get(0).split(";")[0];
            } else {
                System.out.println("header中没有获取到set-cookie信息");
            }
            //访问zosmf获取token
            String loginUrlOverHttps = zosmfUrlOverHttps+"LoginServlet";
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString());
            headers.add("Referer", zosmfUrlOverHttps);//欺骗服务器这不是csrf这不是csrf这不是csrf
            //添加表单数据
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("requestType", "Login");
            map.add("username", account.get("account"));
            map.add("password", account.get("password"));
            //request
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(loginUrlOverHttps, request, String.class);
            if (!response.toString().contains("Set-Cookie:")) {
                return ResponseEntity.status(401).body("unauthorized");
            }
            ZOSMF_LtpaToken2 = response.toString().split("Set-Cookie:\"|; Path")[1];

            session.setAttribute("ZOSMF_JSESSIONID", ZOSMF_JSESSIONID);
            session.setAttribute("ZOSMF_LtpaToken2", ZOSMF_LtpaToken2);
            session.setAttribute("ZOSMF_Address", ZOSMF_Address);
        } else {
            System.out.println("session已经存在");
        }
        System.out.println(ZOSMF_Address);
        System.out.println(ZOSMF_JSESSIONID);
        System.out.println(ZOSMF_LtpaToken2);
        return ResponseEntity.ok("successful");
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/logoff",method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpSession session){
        session.removeAttribute("ZOSMF_JSESSIONID");
        session.removeAttribute("ZOSMF_LtpaToken2");
        session.removeAttribute("ZOSMF_Address");
        System.out.println("登出");
        return ResponseEntity.ok("successful");
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/racf", method = RequestMethod.PUT)
    public ResponseEntity<String> racfCommand(@RequestBody String command, HttpSession session) {
        //获取session数据
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            return ResponseEntity.status(401).body("unauthorized");
        } else {//把racf命令包装成jcl执行
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //提交jcl的zosmf地址
            String urlOverHttps = "https://"+ZOSMF_Address.toString()+"/zosmf/restjobs/jobs";
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //添加body中的text
            String line1 = "//RACFTRY JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,";
            String line2 = "// TIME=1                                       ";
            String line3 = "//SEND EXEC PGM=IKJEFT01                        ";
            String line4 = "//SYSPRINT DD DUMMY                             ";
            String line5 = "//SYSTSPRT DD SYSOUT=*                          ";
            String line6 = "//SYSTSIN  DD *                                 ";
            String line7 = "  " + command;
            String line8 = "/*                                              ";
            String allLines = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", line1, line2, line3, line4, line5, line6, line7, line8);
            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(allLines, headers);
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //每隔100毫秒查看一次作业结果，等待两秒
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.currentThread().sleep(100);//毫秒
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://"+ZOSMF_Address.toString()+"/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果的request
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    urlOverHttps = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> result = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, String.class);
                    return ResponseEntity.ok(result.getBody());
                }
            }
            //超时
            return ResponseEntity.status(202).body("time out");
        }
    }
/*
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/racf/user", method = RequestMethod.POST)
    public ResponseEntity<String> addUser() {

    }*/
}
