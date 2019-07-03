package com.sse.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class StudentsController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.OK)
    public class SqlOKException extends RuntimeException{}

    //返回学生报告
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getdraft", method = RequestMethod.GET)
    public List<Map<String, Object>> getDraft(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }
        else {
            String uid = req.get("uid");
            //String uid = ZOSMF_Account.toString();
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String question_id = req.get("question_id");
            String sql = "select answer from report where and uid=? and lab=? and step=? and lower_lab=? and question_id=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid, lab, step, lower_lab, question_id);
            if (list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }
    //学生上传草稿
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/subAnswer", method = RequestMethod.POST)
    public List<Map<String, Object>> subAnswer(@RequestBody Map<String, String> req, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = req.get("uid");
            //String uid = ZOSMF_Account.toString();
            String lab = req.get("lab");
            String answer = req.get("answer");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String question_id = req.get("question_id");
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=? and question_id=? and is_draft=1;";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab, question_id);
            if (result_list.size() == 0) {
                String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, is_draft, answer) values ( ?, ?, ?, ?, ?, ?, ?);";
                jdbcTemplate.update(sql_insert, uid, lab, step, lower_lab, question_id, "1", answer);
            } else {
                String sql_update = "update report set answer=? where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
                jdbcTemplate.update(sql_update, answer, uid, lab, step, lower_lab, question_id);
            }
            throw new SqlOKException();
        }
    }

    //学生确认提交
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/confirmAnswer", method = RequestMethod.POST)
    public List<Map<String, Object>> confirmAnswer(@RequestBody Map<String, String> req, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }
        else {
            String uid = req.get("uid");
            //String uid = ZOSMF_Account.toString();
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String question_id = req.get("question_id");
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=? and question_id=? and is_draft=1;";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab, question_id);
            if (result_list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                String sql_update = "update report set is_draft=? where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
                jdbcTemplate.update(sql_update, "0", uid, lab, step, lower_lab, question_id);
                throw new SqlOKException();
            }
        }
    }
}
