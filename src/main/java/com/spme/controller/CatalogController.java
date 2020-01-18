package com.spme.controller;

import com.spme.domain.JCLInfo;
import com.spme.domain.JobInfo;
import com.spme.utils.AuthUtil;
import com.spme.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * @author 徐仁和
 */
@Controller
public class CatalogController {

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/catalog", method = RequestMethod.PUT)
    public ResponseEntity<JCLInfo> UserCatalog(@RequestBody String jclCommand, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (AuthUtil.notLogin(session)) {
            //没有token信息，授权失败
            return ResponseEntity.status(401).body(null);
        } else {
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs";
            HttpHeaders req_headers = new HttpHeaders();
            req_headers.setContentType(MediaType.TEXT_PLAIN);
            req_headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);

            HttpEntity<String> req_sub = new HttpEntity<>(jclCommand, req_headers);
            ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.PUT, req_sub, JobInfo.class);

            //每隔100毫秒查看一次作业结果，等待两秒
            if (responseSub.getBody() != null) {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(100);//毫秒
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    //查询执行状态的地址
                    urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
                    //查询结果的request
                    HttpEntity<String> requestQur = new HttpEntity<>(req_headers);
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
