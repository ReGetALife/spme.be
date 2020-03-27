package com.spme.controller;


import com.spme.service.LoginService;
import com.spme.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * login, logout and getting login info
 *
 * @author 李庆国
 */
@Controller
public class LoginController {

    /**
     * Do not new an object, if you did, auto injection would not work e.g. you would
     * get a null variable while using auto injection inside that object
     */
    @Resource
    private LoginService ls;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> account, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            switch (ls.login(account, session)) {
                case "unauthorized":
                    return ResponseEntity.status(401).body("unauthorized");
                case "time out":
                    return ResponseEntity.status(502).body("zosmf time out");
            }
        }
        ls.setRole(session);
        return ResponseEntity.ok("successful");
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Object> loginInfo(HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            return ResponseEntity.ok().body(ls.getLoginInfo(session));
        }
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/logoff", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpSession session) {
        ls.logoff(session);
        return ResponseEntity.ok("successful");
    }
}

