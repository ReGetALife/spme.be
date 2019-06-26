package com.example.demo;


import org.apache.http.impl.client.CloseableHttpClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class SmsController {


    //获取数据集列表
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/getdslist", method = RequestMethod.GET)
    public ResponseEntity<String> getSDSList(@RequestParam String dsName, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds?dslevel="+dsName;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            HttpEntity<String> requestList = new HttpEntity<>(headers);

            ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestList, String.class);
            return ResponseEntity.ok(responseList.getBody());
        }
    }

    //获取分区数据集成员
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/getpdsmemberlist", method = RequestMethod.GET)
    public ResponseEntity<String> getPDSMember(@RequestParam String dsName, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
//        String dsName="ST010.SEQDS";
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+dsName+"/member";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            HttpEntity<String> requestList = new HttpEntity<>(headers);

            ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestList, String.class);
            return ResponseEntity.ok(responseList.getBody());
        }
    }

    //删除数据集
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/deleteds", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSDS(@RequestParam String dsName, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
//        String dsName="ST010.SEQDS";
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+dsName;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            HttpEntity<String> requestList = new HttpEntity<>(headers);

            ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.DELETE, requestList, String.class);
            return ResponseEntity.ok(responseList.getBody());
        }
    }


    //删除分区数据集成员
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/deletepdsmember", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePDSMenber(@RequestParam String dsName, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
//        String dsName=null;
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+dsName;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            HttpEntity<String> requestList = new HttpEntity<>(headers);

            ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.DELETE, requestList, String.class);
            return ResponseEntity.ok(responseList.getBody());
        }
    }

    //获取数据集内容
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/getds", method = RequestMethod.GET)
    public ResponseEntity<String> getDataset(@RequestParam String dsName, HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
//        String dsName=null;
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+dsName;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            HttpEntity<String> requestCont = new HttpEntity<>(headers);

            ResponseEntity<String> responseCont = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestCont, String.class);
            return ResponseEntity.ok(responseCont.getBody());
        }
    }


    //提交作业
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @RequestMapping(value = "/sms/subjob",method = RequestMethod.PUT)
    public ResponseEntity<String> subJob(@RequestParam List<String> lists,HttpSession session){
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if(ZOSMF_JSESSIONID==null || ZOSMF_LtpaToken2 == null){
            //没有token信息，授权失败
            return ResponseEntity.status(401).body("unauthorized");
        }else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //url
            String url ="https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs";
            //hearders
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie",ZOSMF_JSESSIONID.toString()+";"+ZOSMF_LtpaToken2);
            //body
            StringBuffer sb = new StringBuffer();
            for(String item :lists){
                sb.append(item+"\n");
            }
            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(url, HttpMethod.PUT,requestSub,JobInfo.class);

            //query job's status
            for(int i = 0; i<20;i++){
                try {
                    Thread.currentThread().sleep(100);//毫秒
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                url="https://10.60.43.8:8800/zosmf/restjobs/jobs/"+responseSub.getBody().getJobname()+"/"+responseSub.getBody().getJobid();
                //查询结果的request
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(url,HttpMethod.GET,requestQur,JobInfo.class);
                //判断作业状态
                if(responseQur.getBody().getStatus().equals("OUTPUT")){
                    //查询执行结果的地址
                    url=url+"/files/102/records";
                    ResponseEntity<String> result = new RestTemplate(requestFactory).exchange(url,HttpMethod.GET,requestQur,String.class);
                    return ResponseEntity.ok(result.getBody());
                }
            }
            //超时
            return ResponseEntity.status(202).body("time out");
        }
    }


    //将code写入到数据集中
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @RequestMapping(value = "/sms/writeds",method = RequestMethod.PUT)
    //para jcl-content + dataset-name
    public ResponseEntity<String> writeDS(@RequestParam String dsname,@RequestParam List<String> jclList, HttpSession session){
        //获取session数据
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if(ZOSMF_JSESSIONID==null || ZOSMF_LtpaToken2 == null){
            //没有token信息，授权失败
            return ResponseEntity.status(401).body("unauthorized");
        }else{
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //url
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+dsname;
            //headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie",ZOSMF_JSESSIONID.toString()+";"+ZOSMF_LtpaToken2);
            //body
            StringBuffer sb = new StringBuffer();
            for(String item:jclList){
                sb.append(item+"\n");
            }
            String jclStr = sb.toString();
            //start request
            HttpEntity<String> requestWrite = new HttpEntity<>(jclStr, headers);
            //get response
            ResponseEntity<String> responseWritte = new RestTemplate(requestFactory).exchange(url,HttpMethod.PUT,requestWrite,String.class);
            return ResponseEntity.ok(responseWritte.getBody());
        }
    }


    //创建数据集
    @CrossOrigin(origins = "*",allowCredentials = "true")
    @RequestMapping(value="/sms/createds",method = RequestMethod.POST)
    public ResponseEntity<String> createDS(@RequestBody DatasetInfo ds, HttpSession session){
        //获取session数据
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if(ZOSMF_JSESSIONID==null || ZOSMF_LtpaToken2 == null){
            return ResponseEntity.status(401).body("unauthorized");
        }
        else{
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //接收前端数据
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/"+ds.getDsname();
            //headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie",ZOSMF_JSESSIONID.toString()+";"+ZOSMF_LtpaToken2);
            //body
            JSONObject object = new JSONObject();
            object.put("volser",ds.getVolser());
            object.put("unit", ds.getUnit());
            object.put("dsorg", ds.getDsorg());
            object.put("alcunit",ds.getAlcunit());
            object.put("primary", ds.getPrimary());
            object.put("secondary", ds.getSecondary());
            object.put("dirblk", ds.getDirblk());
            object.put("avgblk", ds.getAvgblk());
            object.put("recfm", ds.getRecfm());
            object.put("blksize", ds.getBlksize());
            object.put("lrecl", ds.getLrecl());
            //start request
            HttpEntity<JSONObject> requestcrt = new HttpEntity<>(object, headers);
            //get response
            ResponseEntity<String > responseCrt = new RestTemplate(requestFactory).postForEntity(url, requestcrt, String.class);

            return ResponseEntity.ok(responseCrt.getBody());

        }

    }


}


