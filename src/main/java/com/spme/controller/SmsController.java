package com.spme.controller;


import com.spme.domain.DatasetInfo;
import com.spme.domain.JCLInfo;
import com.spme.domain.JobInfo;
import com.spme.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 冯濛
 */
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
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds?dslevel=" + dsName;
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
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + dsName + "/member";
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
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + dsName;
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
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + dsName;
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
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + dsName;
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
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/subjob", method = RequestMethod.PUT)
    public ResponseEntity<JCLInfo> subJob(@RequestBody Map<String, List<String>> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数  List类型的String 容器
            List<String> list = map.get("jclList");

            StringBuffer sb = new StringBuffer();
            for (String item : list) {
                //将jcl写入sb
                sb.append(list + "\n");
            }
            System.out.println(sb.toString());

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }


    //将code写入到数据集中
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/writeds", method = RequestMethod.PUT)
    //para jcl-content + dataset-name
    public ResponseEntity<String> writeDS(@RequestBody Map<String, Object> map, HttpSession session) {
        //获取session数据
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null) {
            //没有token信息，授权失败
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //url
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + map.get("dsName");
            //headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            StringBuffer sb = new StringBuffer();
            Collection<Object> jclList = (Collection<Object>) map.get("jclList");
            System.out.println(jclList);
            for (Object item : jclList) {
                sb.append(item + "\n");
            }
            String jclStr = sb.toString();
            //start request
            HttpEntity<String> requestWrite = new HttpEntity<>(jclStr, headers);
            //get response
            ResponseEntity<String> responseWritte = new RestTemplate(requestFactory).exchange(url, HttpMethod.PUT, requestWrite, String.class);
            return ResponseEntity.ok(responseWritte.getBody());
        }
    }


    //创建数据集
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/createds", method = RequestMethod.POST)
    public ResponseEntity<String> createDS(@RequestBody DatasetInfo ds, HttpSession session) {
        //获取session数据
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //接收前端数据
//            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/ST007.AAAAE.TEST";
            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/" + ds.getDsname();
            System.out.println(ds.getDsname());
            //headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            JSONObject object = new JSONObject();
            object.put("volser", ds.getVolser());
            object.put("unit", ds.getUnit());
            object.put("dsorg", ds.getDsorg());
            object.put("alcunit", ds.getAlcunit());
            object.put("primary", ds.getPrimary());
            object.put("secondary", ds.getSecondary());
            object.put("dirblk", ds.getDirblk());
            object.put("avgblk", ds.getAvgblk());
            object.put("recfm", ds.getRecfm());
            object.put("blksize", ds.getBlksize());
            object.put("lrecl", ds.getLrecl());
//            object.put("volser","BYWK00");
//            object.put("unit","3390");
//            object.put("dsorg", "PO");
//            object.put("alcunit","TRK");
//            object.put("primary", 10);
//            object.put("secondary",10);
//            object.put("dirblk", 5);
//            object.put("avgblk", 500);
//            object.put("recfm","FB");
//            object.put("blksize", 400);
//            object.put("lrecl", 80);

            System.out.println(object.toJSONString());

            //start request
            HttpEntity<JSONObject> requestcrt = new HttpEntity<>(object, headers);
            //get response
            ResponseEntity<String> responseCrt = new RestTemplate(requestFactory).postForEntity(url, requestcrt, String.class);

            return ResponseEntity.ok(responseCrt.getBody());

        }

    }


    //ISMF - 4.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/4/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob41(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数  参数列表
            //学生账号
            String id = map.get("id");
            //SCDS -name
            String Scds = map.get("scds");
            //sys -name
            String SysName = map.get("sys");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01041 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//STEP1   EXEC ACBJBAOB,\n");
            sb.append("//        TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSUDUMP DD  SYSOUT=*\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAB1 DEFINE+\n");
            sb.append("SCDS(ST010.SMS1.SCDS) +\n");
            sb.append("DESCR() +\n");
            sb.append("DEFMC() +\n");
            sb.append("DEFUNIT() +\n");
            sb.append("BYTPTRK(56664) +\n");
            sb.append("TRKPCYL(15) +\n");
            sb.append("DSSEPPL() +\n");
            sb.append("ADDSYS(P390) +\n");
            sb.append("ADDGRP() +\n");
            sb.append("UPDHLVLSCDS() +\n");
            sb.append(") +\n");
            sb.append("BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n");
            sb.append("/*\n");
            sb.append("/*\n");
            sb.append("//STEP3   EXEC ACBJBAOB,\n");
            sb.append("//        TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSUDUMP DD  SYSOUT=*\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAB1 DISPLAY +\n");
            sb.append("SCDS('ST010.SMS1.SCDS') +\n");
            sb.append(") +\n");
            sb.append("BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n");
            sb.append("/*");


            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF - 5.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/5/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob51(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数 --参数列表
            String id = map.get("id"); //作为prefix使用
            String Scds = map.get("scds");
            String dsName = map.get("dsName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01051 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD  *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIC SAVE DCNAMES +\n");
            sb.append("SCDS('ST010.SMS1.SCDS') DATACLAS(DCSDS)) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);
            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF -5.2
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/5/2", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob52(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String dsName = map.get("dsName");
            StringBuffer sb = new StringBuffer();
            sb.append("//ST01052 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD    *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIC SAVE DCNAMES +\n");
            sb.append("SCDS('ST010.SMS1.SCDS') DATACLAS(DCPDS)) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");
            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }


    //ISMF -5.3
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/5/3", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob53(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String dsName = map.get("dsName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01053 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD    *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIC SAVE DCNAMES +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");
            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }


    //ismf -6.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/6/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob61(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            String id = map.get("id");
            String Scds = map.get("scds");
            String scName = map.get("scName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01061 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD    *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIF SAVE SCNAMES +" + "\n");
            sb.append("SCDS(‘ST010.SMS1.SCDS’) STORCLAS(SCTEST)) +" + "\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF -7.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/7/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob71(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String mcName = map.get("mcName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01071 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD    *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAID SAVE MCNAMES +\n");
            sb.append("SCDS('ST010.SMS1.SCDS') MGMTCLAS(MCTEST)) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ismf -8.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/8/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob81(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);

            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String sgName = map.get("sgName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST01081 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB    JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//GENDCLST EXEC  ACBJBAOB,\n");
            sb.append("//         PLIB1=SYS1.DGTPLIB,\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD    *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIG SAVE SGNAMES +\n");
            sb.append("SCDS('ST010.SMS1.SCDS') STORGRP(SG) STGTYPE(POOL) SPACEGB(N))+\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");


            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }


    //ISMF 11.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/11/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob111(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String srcName = map.get("srcName");
            String memName = map.get("memName");
            String listName = map.get("listName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST010111 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//STEP1   EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAO1 +\n");
            sb.append("ACSSRC('ST010.SMS.ACS') MEMBER(SC) +\n");
            sb.append("SCDS('ST010.SMS.SCSDS') LISTNAME(ST010.LIST) +\n");
            sb.append("UPDHLVLSCDS()) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");


            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF 11.2
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/11/2", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob112(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //get paragram
            String id = map.get("id");
            String Scds = map.get("scds");
            String srcName = map.get("srcName");
            String memName = map.get("memName");
            String listName = map.get("listName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST010111 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//STEP1   EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAO1 +\n");
            sb.append("ACSSRC('ST010.SMS.ACS') MEMBER(DC) +\n");
            sb.append("SCDS('ST010.SMS.SCSDS') LISTNAME(ST010.LIST) +\n");
            sb.append("UPDHLVLSCDS()) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");


            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF 11.3
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/11/3", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob113(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //get para
            String id = map.get("id");
            String Scds = map.get("scds");
            String listName = map.get("listName");
            String memName = map.get("memName");
            String srcName = map.get("srcName");
            StringBuffer sb = new StringBuffer();
            sb.append("//ST010111 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//STEP1   EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAO1 +\n");
            sb.append("ACSSRC('ST010.SMS.ACS') MEMBER(MC) +\n");
            sb.append("SCDS('ST010.SMS.SCSDS') LISTNAME(ST010.LIST) +\n");
            sb.append("UPDHLVLSCDS()) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");


            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }


    //ISMF 11.4
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/11/4", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob114(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            String id = map.get("id");
            String Scds = map.get("scds");
            String srcName = map.get("srcName");
            String memName = map.get("memName");
            String listName = map.get("listName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST010111 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//STEP1   EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAO1 +\n");
            sb.append("ACSSRC('ST010.SMS.ACS') MEMBER(SG) +\n");
            sb.append("SCDS('ST010.SMS.SCSDS') LISTNAME(ST010.LIST) +\n");
            sb.append("UPDHLVLSCDS()) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ISMF 12.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/12/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob121(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            String id = map.get("id");
            String Scds = map.get("scds");
            StringBuffer sb = new StringBuffer();
            sb.append("//ST010112 JOB (ACCT),'ST010',MSGCLASS=H,\n");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//TRANSLAT EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAO2 SCDS('ST010.SMS.SCDS') TYPE(*) +\n");
            sb.append("LISTNAME('ST010.LIST1') +\n");
            sb.append("UPDHLVLSCDS()) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }

    //ismf 13.1
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/sms/ismf/13/1", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> subJob131(@RequestBody Map<String, String> map, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            return new ResponseEntity("unauthorized", HttpStatus.valueOf(401));
        } else {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //jcljob提交地址
            String jclAddress = ZOSMF_Address.toString();
            String urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
            //body
            //获取参数
            String id = map.get("id");
            String Scds = map.get("scds");
            String memName = map.get("memName");
            String listName = map.get("listName");
            String testName = map.get("testName");

            StringBuffer sb = new StringBuffer();
            sb.append("//ST010113 JOB (ACCT),'ST010',MSGCLASS=H,");
            sb.append("//      NOTIFY=ST010,CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n");
            sb.append("//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n");
            sb.append("//TRANSLAT EXEC ACBJBAOB,\n");
            sb.append("//         PLIB1='SYS1.DGTPLIB',\n");
            sb.append("//         TABL2=ST010.TEST.ISPTABL\n");
            sb.append("//SYSTSIN  DD *\n");
            sb.append("PROFILE PREFIX(ST010)\n");
            sb.append("ISPSTART CMD(ACBQBAIA +\n");
            sb.append("SCDS('ST010.SMS.SCDS') +\n");
            sb.append("TESTBED('ST010.SMS.TEST') MEMBER(CASE1) +\n");
            sb.append("DC(Y) SC(Y) MC(Y) SG(Y) +\n");
            sb.append("LISTNAME(NEW.LISTING)) +\n");
            sb.append("NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n");
            sb.append("/*");

            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(sb.toString(), headers);
            //响应内容
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            //query job's status
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.currentThread().sleep(1000);//
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                //查询执行状态的地址
                urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                //查询结果
                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                //判断作业状态
                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
                    //查询执行结果的地址
                    JCLInfo res_jclinfo = new JCLInfo();
                    String JESMSGLG_url = urlOverHttps + "/files/2/records";
                    String JESJCL_url = urlOverHttps + "/files/3/records";
                    String JESYSMSG_url = urlOverHttps + "/files/4/records";
                    String SYSPRINT_url = urlOverHttps + "/files/102/records";
                    ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                    ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESJCL(res_JESJCL.getBody());
                    ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                    res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
//                    ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
//                    res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                    return new ResponseEntity<JCLInfo>(res_jclinfo, HttpStatus.OK);
                }
            }
            //超时
            return new ResponseEntity("time out", HttpStatus.valueOf(202));
        }
    }
}


