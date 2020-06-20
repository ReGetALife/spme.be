package com.spme.service;

import com.spme.dao.TeacherDao;
import com.spme.utils.AuthUtil;
import com.spme.utils.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李庆国
 */
@Service
public class LoginService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TeacherDao teacherDao;

    public String login(Map<String, String> account, HttpSession session) {
        try {
            Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
            Object ZOSMF_LtpaToken2;
            Object ZOSMF_Address;
            Object ZOSMF_Account;

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
                return "unauthorized";
            }
            ZOSMF_LtpaToken2 = response.toString().split("Set-Cookie:\"|; Path")[1];

            session.setAttribute("ZOSMF_JSESSIONID", ZOSMF_JSESSIONID);
            session.setAttribute("ZOSMF_LtpaToken2", ZOSMF_LtpaToken2);
            session.setAttribute("ZOSMF_Address", ZOSMF_Address);
            session.setAttribute("ZOSMF_Account", ZOSMF_Account);

            return "successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "time out";
        }
    }

    /**
     * fake login bypassing zosmf
     * for develop only
     */
    @SuppressWarnings("unused")
    public String login2(Map<String, String> account, HttpSession session) {
        session.setAttribute("ZOSMF_JSESSIONID", "fakeId");
        session.setAttribute("ZOSMF_LtpaToken2", "fakeToken");
        session.setAttribute("ZOSMF_Address", account.get("address"));
        session.setAttribute("ZOSMF_Account", account.get("account").toUpperCase());
        return "successful";
    }

    public void setRole(HttpSession session) {
        //判断是否教师登录
        session.setAttribute("is_teacher", "no");
        try {
            //String sql = "select * from teacher where id=?";
            //List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, session.getAttribute("ZOSMF_Account").toString());
            if (teacherDao.existsById(session.getAttribute("ZOSMF_Account").toString())) {
                //存在教师表中，赋予教师身份
                session.setAttribute("is_teacher", "yes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getLoginInfo(HttpSession session) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", session.getAttribute("ZOSMF_Account").toString().toUpperCase());
        if (AuthUtil.notTeacherLogin(session))
            map.put("role", "student");
        else
            map.put("role", "teacher");
        return map;
    }

    public void logoff(HttpSession session) {
        session.removeAttribute("ZOSMF_JSESSIONID");
        session.removeAttribute("ZOSMF_LtpaToken2");
        session.removeAttribute("ZOSMF_Address");
        session.removeAttribute("ZOSMF_Account");
    }
}
