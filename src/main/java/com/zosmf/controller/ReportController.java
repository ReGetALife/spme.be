package com.zosmf.controller;

import com.zosmf.utils.AuthUtil;
import com.zosmf.utils.PDFUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 徐仁和
 * @author 李庆国
 */
@RestController
@RequestMapping("/db")
public class ReportController {

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

    //获取所有学生
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public List<Map<String, Object>> getStudents(HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new ResourceNotFoundException();
        } else {
            String sql = "select * from student";
            return jdbcTemplate.queryForList(sql);
        }
    }

    //根据主机号获取学生 参数为要查找的学生主机号
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getStudent", method = RequestMethod.POST)
    public List<Map<String, Object>> getStudentByID(@RequestBody String uid, HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql = "select * from student where uid=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid);
            if (list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }

    //老师提交分数和评论
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/subScore", method = RequestMethod.POST)
    public List<Map<String, Object>> subScores(@RequestBody Map<String, String> req, HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = req.get("uid");
            //String uid = ZOSMF_Account.toString();
            String lab = req.get("lab");
            String comment = req.get("comment");
            String score = req.get("score");
            String sql_search = "select * from result where uid=? and lab=?;";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab);
            if (result_list.size() == 0) {
                String sql_insert = "insert into result (uid, score, comment, lab) values ( ?, ?, ?, ?);";
                jdbcTemplate.update(sql_insert, uid, score, comment, lab);
            } else {
                int tag = (int) result_list.get(0).get("is_release");
                if (tag == 1) {
                    throw new ResourceNotFoundException();
                } else {
                    String sql_update = "update result set score=?, comment=? where uid=? and lab=?;";
                    jdbcTemplate.update(sql_update, score, comment, uid, lab);
                }
            }
            throw new SqlOKException();
        }
    }

    //老师发布评分和评论
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/reScore", method = RequestMethod.POST)
    public List<Map<String, Object>> releaseScore(@RequestBody Map<String, String> data, HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql_search = "select * from result";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search);
            if (result_list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                String sql_update = "update result set is_release=1 where lab=?";
                jdbcTemplate.update(sql_update, data.get("lab"));
            }
            throw new SqlOKException();
        }
    }

    //学生查看发布的成绩，未发布时返回返回ResourseNotFound的异常
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/checkScore", method = RequestMethod.GET)
    public List<Map<String, Object>> checkScore(HttpSession session) {
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            //String uid = "ST009";
            System.out.println(uid);
            String sql_search = "select * from result where uid=?";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid);
            List<Map<String, Object>> result_return = new ArrayList<>();
            System.out.println(result_list);
            if (result_list.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                for (Map<String, Object> stringObjectMap : result_list) {
                    if (stringObjectMap.get("is_release").equals(1)) {
                        result_return.add(stringObjectMap);
                    }
                }
            }
            if (result_return.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                return result_return;
            }
        }
    }

    //根据主机账号和实验名预览pdf，仅主机账号本人使用
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getReports", method = RequestMethod.GET)
    public void viewPDF(@RequestParam String lab, HttpSession session, HttpServletResponse response) throws IOException {
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            //生成pdf
            String uid = session.getAttribute("ZOSMF_Account").toString();
            PDFUtil.generatePDF(uid, lab, pdfBasePath + "preview", jdbcTemplate);
            //返回实验报告pdf
            String filename = uid + lab + ".pdf";
            File file = new File(pdfBasePath + "preview/" + filename);
            PDFUtil.downloadPDF(file,response);
        }
    }

    //提供给老师的接口，批量导入students
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/saveStudents", method = RequestMethod.POST)
    public String saveStudents(@RequestBody List<Map<String, String>> reqs, HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            for (Map<String, String> req : reqs) {
                String uid = req.get("uid");
                String sid = req.get("sid");
                String sname = req.get("name");
                String sql = "insert into student (uid, sid, name) values (?, ?, ?);";
                jdbcTemplate.update(sql, uid, sid, sname);
            }
            throw new SqlOKException();
        }
    }

    //获取某个实验的提交学生列表
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/submitted", method = RequestMethod.GET)
    public ResponseEntity<List<String>> submitted(@RequestParam String lab, HttpSession session) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            File dir = new File(pdfBasePath + "submitted");
            List<String> list = new ArrayList<>();
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file :
                            files) {
                        if (file.getName().endsWith(lab + ".pdf"))
                            list.add(file.getName().split(lab + ".pdf")[0]);
                    }
                }
            }
            return ResponseEntity.ok(list);
        }
    }

    //下载单个pdf，需要主机账号和实验名
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/downloadPDF", method = RequestMethod.POST)
    public void downloadPDF(@RequestBody Map<String, Object> req, HttpSession session, HttpServletResponse response) {
        if (!AuthUtil.checkTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String dir = pdfBasePath + "submitted/";
            try {
                String uid = req.get("uid").toString();
                String lab = req.get("lab").toString();
                String filename = uid + lab + ".pdf";
                File file = new File(dir + filename);
                PDFUtil.downloadPDF(file,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取大实验、小实验、步骤下的所有问题
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    public List<Map<String, Object>> getQuestions(@RequestBody Map<String, String> req, HttpSession session) {
        if (!AuthUtil.checkLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String sql = "select question_id, question from question where lab=? and step=? and lower_lab=?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, lab, step, lower_lab);
            if (result.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                return result;
            }
        }
    }
}