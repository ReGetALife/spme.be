package com.zosmf.controller;


import com.zosmf.utils.AuthUtil;
import com.zosmf.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录，登出和登录状态检查
 *
 * @author 李庆国
 */
@Controller
public class LoginController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> account, HttpSession session, HttpServletRequest httpServletRequest) {
        if (AuthUtil.notLogin(session)) {
            Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
            Object ZOSMF_LtpaToken2;
            Object ZOSMF_Address;
            Object ZOSMF_Account;
            System.out.println("session未保存，从zosmf获取token并保存至session。");

            //获取zosmf地址
            ZOSMF_Address = account.get("address");
            //获取登录账户名
            ZOSMF_Account = account.get("account").toUpperCase();
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
            if (ZOSMF_JSESSIONID != null) {
                headers.add("Cookie", ZOSMF_JSESSIONID.toString());
            }
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
        //判断是否教师登录
        session.setAttribute("is_teacher", "no");
        try {
            String sql = "select * from teacher where id=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, session.getAttribute("ZOSMF_Account"));
            if (list.size() > 0) {
                //存在教师表中，赋予教师身份，同时记录连接地址
                System.out.println(httpServletRequest.getRemoteAddr() + " login as teacher: " + session.getAttribute("ZOSMF_Account"));
                session.setAttribute("is_teacher", "yes");
            } else
                System.out.println(httpServletRequest.getRemoteAddr() + " login as student: " + session.getAttribute("ZOSMF_Account"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("successful");
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Object> loginInfo(HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("uid", session.getAttribute("ZOSMF_Account").toString().toUpperCase());
            if (AuthUtil.notTeacherLogin(session))
                map.put("role", "student");
            else
                map.put("role", "teacher");
            return ResponseEntity.ok().body(map);
        }
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/logoff", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("ZOSMF_JSESSIONID");
        session.removeAttribute("ZOSMF_LtpaToken2");
        session.removeAttribute("ZOSMF_Address");
        session.removeAttribute("ZOSMF_Account");
        System.out.println("登出");
        return ResponseEntity.ok("successful");
    }
}

