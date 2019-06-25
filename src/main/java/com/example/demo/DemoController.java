package com.example.demo;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class DemoController {

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> account, HttpSession session) {
        //获取session
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");

        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            System.out.println("session未保存，从zosmf获取token并保存至session。");

            //获取zomsmf地址
            ZOSMF_Address = account.get("address");
            //获取登录账户名
            ZOSMF_Account = account.get("account");
            //禁用ssl证书校验
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory
                    = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            //访问zosmf获取jsessionid
            String zosmfUrlOverHttps = "https://" + ZOSMF_Address.toString() + "/zosmf/";
            HttpHeaders httpHeaders = new RestTemplate(requestFactory).headForHeaders(zosmfUrlOverHttps);
            List<String> setCookie = httpHeaders.get("Set-Cookie");
            if (setCookie != null) {
                ZOSMF_JSESSIONID = setCookie.get(0).split(";")[0];
            } else {
                System.out.println("header中没有获取到set-cookie信息");
            }
            //访问zosmf获取token
            String loginUrlOverHttps = zosmfUrlOverHttps + "LoginServlet";
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
            session.setAttribute("ZOSMF_Account", ZOSMF_Account);
        } else {
            System.out.println("session已经存在");
        }
        System.out.println(ZOSMF_Address);
        System.out.println(ZOSMF_JSESSIONID);
        System.out.println(ZOSMF_LtpaToken2);
        return ResponseEntity.ok("successful");
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> loginInfo(HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            return ResponseEntity.ok(ZOSMF_Account.toString().toUpperCase());
        }
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/logoff", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("ZOSMF_JSESSIONID");
        session.removeAttribute("ZOSMF_LtpaToken2");
        session.removeAttribute("ZOSMF_Address");
        System.out.println("登出");
        return ResponseEntity.ok("successful");
    }
    
    
    

   
    
    
}
    }

}

