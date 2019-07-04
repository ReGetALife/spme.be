package com.zosmf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db")
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
    @RequestMapping(value = "/getdraft", method = RequestMethod.POST)
    public List<Map<String, Object>> getDraft(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        String uid = ZOSMF_Account.toString();
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }
        else {
            //String uid = req.get("uid");
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            //String question_id = req.get("question_id");
            String sql = "select question_id, answer from report where uid=? and lab=? and step=? and lower_lab=? and is_draft=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid, lab, step, lower_lab, "Y");
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
    public List<Map<String, Object>> subAnswer(@RequestBody List<Map<String, Object>> req, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        String uid = ZOSMF_Account.toString();
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
            String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, answer) values ( ?, ?, ?, ?, ?, ?);";
            String sql_update = "update report set answer=? where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
            for (int i = 0; i < req.size(); i++) {
                //String uid = req.get(i).get("uid").toString();
                String lab = req.get(i).get("lab").toString();
                String answer = req.get(i).get("answer").toString();
                String step = req.get(i).get("step").toString();
                String lower_lab = req.get(i).get("lower_lab").toString();
                String question_id = req.get(i).get("question_id").toString();
                List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab, question_id);
                if (result_list.size() == 0) {
                    jdbcTemplate.update(sql_insert, uid, lab, step, lower_lab, question_id, answer);
                } else {
                    if (result_list.get(0).get("is_draft").equals("N")){
                        throw new ResourceNotFoundException();
                    } else {
                        jdbcTemplate.update(sql_update, answer, uid, lab, step, lower_lab, question_id);
                    }
                }
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
        String uid = ZOSMF_Account.toString();
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }
        else {
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=?;";
            //String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, is_drawft) values ( ?, ?, ?, ?, ?, ?, ?);";
            String sql_update = "update report set is_draft=? where uid=? and lab=? and step=? and lower_lab=?";
            //String uid = req.get("uid");
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab);
            if (result_list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                jdbcTemplate.update(sql_update, "N", uid, lab, step, lower_lab);
            }
            throw new SqlOKException();
        }
    }
}
