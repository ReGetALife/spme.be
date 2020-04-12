package com.spme.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.spme.domain.JCLInfo;
import com.spme.domain.JobInfo;
import com.spme.utils.AuthUtil;
import com.spme.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author 李天阳
 * @deprecated Duplicated with /jcl & /cmd api -- Qingguo Li
 */
@Controller
public class RacfController {


    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/racf/JCLjob", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> racfJCL(@RequestBody Map<String, String> commBody, HttpSession session) {
        //获取session数据
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (AuthUtil.notLogin(session)) {
            //没有token信息，授权失败
            return ResponseEntity.status(401).body(null);
        } else {
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


            StringBuilder command = new StringBuilder();
            if (commBody.get("command") != null && !commBody.get("command").equals("")) {
                command.append(commBody.get("command")).append(" ");
            }
            HttpEntity<String> requestSub = new HttpEntity<>(command.toString(), headers);
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);//毫秒
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //查询执行状态的地址
                if (responseSub.getBody() != null) {
                    urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                    //查询结果的request
                    HttpEntity<String> requestQur = new HttpEntity<>(headers);
                    ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                    //判断作业状态
                    if (responseQur.getBody() != null && responseQur.getBody().getStatus().equals("OUTPUT")) {

                        JCLInfo res_jclinfo = new JCLInfo();
                        String JESMSGLG_url = urlOverHttps + "/files/2/records";
                        String JESJCL_url = urlOverHttps + "/files/3/records";
                        String JESYSMSG_url = urlOverHttps + "/files/4/records";
                        String SYSPRINT_url = urlOverHttps + "/files/102/records";
                        try {
                            ResponseEntity<String> res_JESMSGLG = new RestTemplate(requestFactory).exchange(JESMSGLG_url, HttpMethod.GET, requestQur, String.class);
                            res_jclinfo.setJESMSGLG(res_JESMSGLG.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            ResponseEntity<String> res_JESJCL = new RestTemplate(requestFactory).exchange(JESJCL_url, HttpMethod.GET, requestQur, String.class);
                            res_jclinfo.setJESJCL(res_JESJCL.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            ResponseEntity<String> res_JESYSMSG = new RestTemplate(requestFactory).exchange(JESYSMSG_url, HttpMethod.GET, requestQur, String.class);
                            res_jclinfo.setJESYSMSG(res_JESYSMSG.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
                            res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return new ResponseEntity<>(res_jclinfo, HttpStatus.OK);
                    }
                }
            }

            //超时
            return ResponseEntity.status(202).body(null);
        }
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/racf/inputCommand", method = RequestMethod.POST)
    public ResponseEntity<JCLInfo> command(@RequestBody Map<String, String> commBody, HttpSession session) {

        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
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

            //获取命令
            String command = "";
            if (commBody.get("command") != null && !commBody.get("command").equals("")) {
                command = commBody.get("command");
            }
            //分割多个命令
            String[] cmds = command.split(";");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cmds.length; i++) {
                //整理一下格式，把（连续的）空格、换行替换成一个空格
                cmds[i] = cmds[i].replaceAll("[ \n\r]+", " ");
                cmds[i] = cmds[i].trim();
                //命令太长需要处理
                if (cmds[i].length() > 72) {
                    cmds[i] = cmds[i].replaceAll(" ", " -\n");
                }
                if (cmds[i].length() == 0) {
                    continue;
                }
                if (i == cmds.length - 1) {
                    sb.append(cmds[i]);
                } else {
                    sb.append(cmds[i]).append("\n");
                }
            }
            command = sb.toString();

            //添加body中的text
            String line1 = "//RACFTRY JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,";
            String line2 = "// TIME=2                                       ";
            String line3 = "//SEND EXEC PGM=IKJEFT01                        ";
            String line4 = "//SYSPRINT DD DUMMY                             ";
            String line5 = "//SYSTSPRT DD SYSOUT=*                          ";
            String line6 = "//SYSTSIN  DD *                                 ";
            String line7 = command;
            String line8 = "/*                                              ";
            String allLines = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", line1, line2, line3, line4, line5, line6, line7, line8);
            //提交jcl的request
            HttpEntity<String> requestSub = new HttpEntity<>(allLines, headers);
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, requestSub, JobInfo.class);


            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);//
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //查询执行状态的地址
                if (responseSub.getBody() != null) {
                    urlOverHttps = "https://" + jclAddress + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                    //查询结果
                    HttpEntity<String> requestQur = new HttpEntity<>(headers);
                    ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
                    //判断作业状态
                    if (responseQur.getBody() != null && responseQur.getBody().getStatus().equals("OUTPUT")) {
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
                        ResponseEntity<String> res_SYSPRINT = new RestTemplate(requestFactory).exchange(SYSPRINT_url, HttpMethod.GET, requestQur, String.class);
                        res_jclinfo.setSYSPRINT(res_SYSPRINT.getBody());
                        return new ResponseEntity<>(res_jclinfo, HttpStatus.OK);
                    }
                }
            }
            //超时
            return ResponseEntity.status(202).body(null);
        }
    }
}
