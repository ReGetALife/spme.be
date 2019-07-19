package com.zosmf.controller;


import com.alibaba.fastjson.JSONObject;
import com.zosmf.domain.JobInfo;
import com.zosmf.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
public class RexxController {
    //删除数据集
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/deleteDataset", method = RequestMethod.POST)
    public ResponseEntity<String> exDataset(@RequestBody Map<String, String> map, HttpSession session) {
        String rexxName = map.get("rexxName");
        //获取session
        System.out.println(rexxName);
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        System.out.println(ZOSMF_Address.toString());
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        System.out.println(ZOSMF_Account.toString());
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        System.out.println(ZOSMF_JSESSIONID.toString());
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        System.out.println(ZOSMF_LtpaToken2.toString());
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            HttpEntity<String> requestQur = new HttpEntity<>(headers);
            //删除
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + rexxName;
            System.out.println(urlOverHttps);
            ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.DELETE, requestQur, String.class);
            ResponseEntity.ok(response.getBody());
            System.out.println("123");
            return ResponseEntity.ok(response.getBody());
        }
    }


    //获取数据集信息
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getdataset", method = RequestMethod.GET)
    public ResponseEntity<String> getDataset(@RequestBody Map<String, String> map, HttpSession session) {
        String name = map.get("rexxName");
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + name;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            HttpEntity<String> requestQur = new HttpEntity<>(headers);
            ResponseEntity<String> result = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, String.class);
            return ResponseEntity.ok(result.getBody());
        }
    }

    //建立数据集
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/createDataset", method = RequestMethod.POST)
    public ResponseEntity<String> createDataset(@RequestBody Map<String, String> map, HttpSession session) {
        String rexxName = map.get("rexxName");
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //提交jcl的zosmf地址
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + rexxName;
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            JSONObject object = new JSONObject();
            object.put("volser", "BYWK00");
            object.put("unit", "3390");
            object.put("dsorg", "PS");
            object.put("alcunit", "TRK");
            object.put("primary", 10);
            object.put("secondary", 5);
            object.put("avgblk", 500);
            object.put("recfm", "FB");
            object.put("blksize", 400);
            object.put("lrecl", 80);
            //request
            HttpEntity<JSONObject> request = new HttpEntity<>(object, headers);
            ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(urlOverHttps, request, String.class);
            return ResponseEntity.ok(response.getBody());
        }
    }

    //建立分区数据集
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/createDatasetP", method = RequestMethod.POST)
    public ResponseEntity<String> createDatasetP(@RequestBody Map<String, String> map, HttpSession session) {
        String rexxName = map.get("rexxName");
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //提交jcl的zosmf地址
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + rexxName;
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            JSONObject object = new JSONObject();
            object.put("volser", "BYWK00");
            object.put("unit", "3390");
            object.put("dsorg", "PS");
            object.put("alcunit", "TRK");
            object.put("primary", 10);
            object.put("secondary", 5);
            object.put("avgblk", 500);
            object.put("recfm", "FB");
            object.put("blksize", 400);
            object.put("dirblk", 10);
            object.put("lrecl", 80);
            //request
            HttpEntity<JSONObject> request = new HttpEntity<>(object, headers);
            ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(urlOverHttps, request, String.class);
            return ResponseEntity.ok(response.getBody());
        }
    }

    //写文件
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/writeDataset", method = RequestMethod.POST)
    public ResponseEntity<String> writeDataset(@RequestBody Map<String, String> map, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        String name = map.get("rexxName");
        String text = map.get("rexxCode");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //提交jcl的zosmf地址
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + name;
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            HttpEntity<String> requestSub = new HttpEntity<>(text, headers);
            ResponseEntity<String> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, String.class);
            return ResponseEntity.ok(responseSub.getBody());
        }

    }


    //将rexx封装在jcl中提交，并查看结果
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/rexx", method = RequestMethod.POST)
    public ResponseEntity<String> rexxEx(@RequestBody Map<String, String> map, HttpSession session) {
        //获取session数据
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        String libname = map.get("libName");
        String rexxname = map.get("rexxName");
        String input = map.get("rexxPut");
        System.out.println(input);
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
            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs";
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //添加body中的text
            String line1 = "//REXX JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,";
            String line2 = "// TIME=1                                       ";
            String line3 = "//REXX EXEC PGM=IKJEFT01                        ";
            String line4 = "//SYSTSPRT DD SYSOUT=*                          ";
            String line5 = "//SYSEXEC DD DSN=" + libname + ",DISP=SHR       ";
            String line6 = "//SYSTSIN DD *                                 ";
            String line7 = "%" + rexxname + " " + input;
            String line8 = "/*                                              ";
            String allLines = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", line1, line2, line3, line4, line5, line6, line7, line8);
            System.out.println(allLines);
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
                urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
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

}
