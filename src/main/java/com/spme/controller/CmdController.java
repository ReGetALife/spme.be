package com.spme.controller;

import com.spme.service.CmdService;
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
public class CmdController {

    @Resource
    private CmdService cs;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/cmd", method = RequestMethod.POST)
    public ResponseEntity<String> runCMD(@RequestBody Map<String, String> body, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        String res;
        if ((res = cs.runCMD(body.get("cmd"), session)) != null) {
            return ResponseEntity.ok(res);
        }
        // time out
        return ResponseEntity.status(202).body(null);
    }
}
