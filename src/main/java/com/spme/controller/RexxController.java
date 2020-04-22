package com.spme.controller;


import com.spme.service.RexxService;
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
 * @author Qingguo Li
 */
@Controller
public class RexxController {

    @Resource
    private RexxService rs;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/rexx", method = RequestMethod.POST)
    public ResponseEntity<String> runRexx(@RequestBody Map<String, String> body, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        String res;
        if ((res = rs.runRexx(session, body.get("rexx"), body.get("params"))) != null) {
            return ResponseEntity.ok(res);
        }
        // time out
        return ResponseEntity.status(202).body(null);
    }

}
