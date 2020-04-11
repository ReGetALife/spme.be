package com.spme.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Qingguo Li
 */
@Service
public class DraftService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public String submitDraft(List<Map<String, Object>> req, HttpSession session) {
        String sql_search = "select * from report where uid=? and lab=?;";
        String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, answer) values ( ?, ?, ?, ?, ?, ?)" +
                "on duplicate key update answer=?;";
        String uid = session.getAttribute("ZOSMF_Account").toString();
        for (Map<String, Object> answerMap : req) {
            String lab = answerMap.get("lab").toString();
            String answer = "";
            if (answerMap.get("answer") != null) {
                answer = answerMap.get("answer").toString();
            }
            String step = answerMap.get("step").toString();
            String lower_lab = answerMap.get("lower_lab").toString();
            String question_id = answerMap.get("question_id").toString();

            try {
                List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab);
                if (result_list.size() > 0 && result_list.get(0).get("is_draft").equals("N")) {
                    return "lab submitted";
                }
                jdbcTemplate.update(sql_insert, uid, lab, step, lower_lab, question_id, answer, answer);
            }catch (Exception e) {
                e.printStackTrace();
                return "no such question";
            }
        }
        return "successful";
    }

    public List<Map<String, Object>> getDraft(String lab, String subLab, String step, HttpSession session) {
        String uid = session.getAttribute("ZOSMF_Account").toString();
        String sql1 = "select question_id from question where lab=? and step=? and lower_lab=?";
        String sql2 = "select question_id, answer from report where uid=? and lab=? and step=? and lower_lab=?";
        List<Map<String, Object>> allStepQuestionsList = jdbcTemplate.queryForList(sql1, lab, step, subLab);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql2, uid, lab, step, subLab);
        for(Map<String, Object> q : allStepQuestionsList) {
            q.put("answer", "");//fallback
            for (Map<String, Object> a: list){
                if(q.get("question_id").toString().equals(a.get("question_id").toString())) {
                    q.put("answer", a.get("answer"));
                    break;
                }
            }
        }
        return allStepQuestionsList;
    }
}
