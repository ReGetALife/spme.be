package com.zosmf.controller;

import com.zosmf.utils.AuthUtil;
import com.zosmf.utils.PDFUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db")
public class StudentsController {
    @Value("${com.zosmf.controller.pdfBasePath}")
    private String pdfBasePath;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private class UnauthorizedException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class ResourceNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.OK)
    private class SqlOKException extends RuntimeException {
    }

    //获取草稿
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getdraft", method = RequestMethod.POST)
    public List<Map<String, Object>> getDraft(@RequestBody Map<String, String> req, HttpSession session) {
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String sql = "select question_id, answer ,is_draft from report where uid=? and lab=? and step=? and lower_lab=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid, lab, step, lower_lab);
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
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
            String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, answer) values ( ?, ?, ?, ?, ?, ?);";
            String sql_update = "update report set answer=? where uid=? and lab=? and step=? and lower_lab=? and question_id=?;";
            String uid = session.getAttribute("ZOSMF_Account").toString();
            for (Map<String, Object> stringObjectMap : req) {
                //String uid = req.get(i).get("uid").toString();
                String lab = stringObjectMap.get("lab").toString();
                String answer = stringObjectMap.get("answer").toString();
                String step = stringObjectMap.get("step").toString();
                String lower_lab = stringObjectMap.get("lower_lab").toString();
                String question_id = stringObjectMap.get("question_id").toString();

                List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab, question_id);
                if (result_list.size() == 0) {
                    jdbcTemplate.update(sql_insert, uid, lab, step, lower_lab, question_id, answer);
                } else {
                    if (result_list.get(0).get("is_draft").equals("N")) {
                        throw new ResourceNotFoundException();
                    } else {
                        jdbcTemplate.update(sql_update, answer, uid, lab, step, lower_lab, question_id);
                    }
                }
            }
            throw new SqlOKException();
        }
    }


    /**
     * @deprecated 以小实验为单位提交实验报告，请使用submitLab以大实验为单位提交实验报告
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/confirmAnswer", method = RequestMethod.POST)
    public List<Map<String, Object>> confirmAnswer(@RequestBody Map<String, String> req, HttpSession session) {
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql_search = "select * from report where uid=? and lab=? and step=? and lower_lab=?;";
            //String sql_insert = "insert into report (uid, lab, step, lower_lab, question_id, is_drawft) values ( ?, ?, ?, ?, ?, ?, ?);";
            String sql_update = "update report set is_draft=? where uid=? and lab=? and step=? and lower_lab=?";
            //String uid = req.get("uid");
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String uid = session.getAttribute("ZOSMF_Account").toString();
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab, step, lower_lab);
            if (result_list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                jdbcTemplate.update(sql_update, "N", uid, lab, step, lower_lab);
            }
            throw new SqlOKException();
        }
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/submitLab", method = RequestMethod.POST)
    public ResponseEntity<String> submitLab(@RequestBody Map<String, String> data, HttpSession session) {
        if (!AuthUtil.checkLogin(session)) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            String sql_update = "update report set is_draft=? where uid=? and lab=?";
            String lab = data.get("lab");
            String account = session.getAttribute("ZOSMF_Account").toString();
            //修改数据库中is_draft字段
            jdbcTemplate.update(sql_update, "N", account, lab);
            //将报告生成到已提交报告文件夹下
            File file = new File(pdfBasePath + "submitted/" + account + lab + ".pdf");
            if (!file.exists())
                PDFUtil.generatePDF(account, lab, pdfBasePath + "submitted", jdbcTemplate);
            return ResponseEntity.ok("successful");
        }
    }
}
