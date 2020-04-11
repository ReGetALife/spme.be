package com.spme.controller;

import com.spme.service.DraftService;
import com.spme.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Qingguo Li
 */
@Controller
public class DraftController {

    @Resource
    private DraftService ds;

    /**
     * Save answers of a list of questions(probably in one step) to draft
     *
     * @return "successful" if success
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public ResponseEntity<String> submitDraft(@RequestBody List<Map<String, Object>> req, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        switch (ds.submitDraft(req, session)) {
            case "lab submitted":
                return ResponseEntity.status(400).body("lab submitted");
            case "no such question":
                return ResponseEntity.status(400).body("no such question");
        }
        return ResponseEntity.ok("successful");
    }

    /**
     * Get drafts of questions in a certain step
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/draft", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getDraft(@RequestParam String lab,@RequestParam String lower_lab,@RequestParam String step, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(ds.getDraft(lab,lower_lab,step, session));
    }
}
