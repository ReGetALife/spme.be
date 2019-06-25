package com.example.demo;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class RacfController {
	
	
	 /*@CrossOrigin(origins = "*", allowCredentials = "true")
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
	            String urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs";
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
	    }*/

	    @CrossOrigin(origins = "*", allowCredentials = "true")
	    @RequestMapping(value = "/racf/inputCommand", method = RequestMethod.POST)
	    public ResponseEntity<String> addUser(@RequestBody Map<String, String> commBody, HttpSession session) {
	//获取session数据
	        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
	        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
	        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
	        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
	            //没有token信息，授权失败
	            return ResponseEntity.status(401).body("unauthorized");
	        } else {
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
	            //构建racf命令
	            
	            StringBuilder command = new StringBuilder(""); 
	            if (commBody.get("command") != null && !commBody.get("command").equals("")) {
	                command.append(commBody.get("command")).append(" ");
	            }
	            
	            System.out.println(command.toString());
	            
	            String formatCommand = command.toString();
	            formatCommand = formatCommand.replace('\n',' ');
	            formatCommand = formatCommand.replace('\r',' ');
	            System.out.println(formatCommand);

	            //添加body中的text
	            String line1 = "//RACFTRY JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,";
	            String line2 = "// TIME=1                                       ";
	            String line3 = "//SEND EXEC PGM=IKJEFT01                        ";
	            String line4 = "//SYSPRINT DD DUMMY                             ";
	            String line5 = "//SYSTSPRT DD SYSOUT=*                          ";
	            String line6 = "//SYSTSIN  DD *                                 ";
	            String line7 = "  " + formatCommand;//command.toString();
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
	                urlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/restjobs/jobs/" + responseSub.getBody().getJobname() + "/" + responseSub.getBody().getJobid();
	                //查询结果的request
	                HttpEntity<String> requestQur = new HttpEntity<>(headers);
	                ResponseEntity<JobInfo> responseQur = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, JobInfo.class);
	                //判断作业状态
	                if (responseQur.getBody().getStatus().equals("OUTPUT")) {
	                    //查询执行结果的地址
	                    urlOverHttps = urlOverHttps + "/files/102/records";
	                    ResponseEntity<String> result = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, requestQur, String.class);
	                    String resultBody = "";
	                    resultBody = result.getBody();
	                    System.out.println(resultBody);
	                    return ResponseEntity.ok(result.getBody());
	                    
	                }
	            }
	            //超时
	            return ResponseEntity.status(202).body("time out");
	        }

	    }
	    
	    

}
