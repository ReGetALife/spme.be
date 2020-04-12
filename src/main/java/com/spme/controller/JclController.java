package com.spme.controller;

import com.spme.domain.JobOutputListItem;
import com.spme.service.JclService;
import com.spme.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Qingguo Li
 */
@Controller
public class JclController {

    @Resource
    private JclService js;

    /**
     * submit a JCL job and get response
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/jcl", method = RequestMethod.POST)
    public ResponseEntity<List<JobOutputListItem>> submitJCL(@RequestBody Map<String, String> body, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        List<JobOutputListItem> res;
        if ((res = js.submitJCL(session, body.get("jcl"))) != null) {
            return ResponseEntity.ok(res);
        } else {
            // time out
            return ResponseEntity.status(202).body(null);
        }
    }
}
