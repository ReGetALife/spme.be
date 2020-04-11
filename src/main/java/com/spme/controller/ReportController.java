package com.spme.controller;

import com.spme.utils.AuthUtil;
import com.spme.utils.PdfUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author 徐仁和
 * @author 李庆国
 */
@RestController
@RequestMapping("/db")
public class ReportController {

    @Value("${com.spme.controller.pdfBasePath}")
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
        if (AuthUtil.notTeacherLogin(session)) {
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
        if (AuthUtil.notTeacherLogin(session)) {
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
        if (AuthUtil.notTeacherLogin(session)) {
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
                String sql_update = "update result set score=?, comment=? where uid=? and lab=?;";
                jdbcTemplate.update(sql_update, score, comment, uid, lab);
            }
            throw new SqlOKException();
        }
    }

    //老师发布评分和评论
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/reScore", method = RequestMethod.POST)
    public List<Map<String, Object>> releaseScore(@RequestBody Map<String, String> data, HttpSession session) {
        if (AuthUtil.notTeacherLogin(session)) {
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

    //学生查看发布的成绩，未发布任何成绩时返回空数组
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/checkScore", method = RequestMethod.GET)
    public List<Map<String, Object>> checkScore(HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String sql_search = "select * from result where uid=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql_search, uid);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : list) {
                if (stringObjectMap.get("is_release").equals(1)) {
                    result.add(stringObjectMap);
                }
            }
            return result;
        }
    }

    //根据主机账号和实验名预览pdf，仅主机账号本人使用
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getReports", method = RequestMethod.GET)
    public void viewPDF(@RequestParam String lab, HttpSession session, HttpServletResponse response) throws IOException {
        if (AuthUtil.notLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            //生成pdf
            String uid = session.getAttribute("ZOSMF_Account").toString();
            PdfUtil.generatePDF(uid, lab, pdfBasePath + "preview", jdbcTemplate);
            //返回实验报告pdf
            String filename = uid + lab + ".pdf";
            File file = new File(pdfBasePath + "preview/" + filename);
            PdfUtil.downloadFile(file, response);
        }
    }

    //提供给老师的接口，批量导入students
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/saveStudents", method = RequestMethod.POST)
    public String saveStudents(@RequestBody List<Map<String, String>> reqs, HttpSession session) {
        if (AuthUtil.notTeacherLogin(session)) {
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
    public ResponseEntity<List<Map<String, Object>>> submitted(@RequestParam String lab, HttpSession session) {
        if (AuthUtil.notTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            File dir = new File(pdfBasePath + "submitted");
            List<Map<String, Object>> list = new ArrayList<>();
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(lab + ".pdf")) {
                            Map<String, Object> map = new HashMap<>();
                            String uid = file.getName().split(lab + ".pdf")[0];
                            map.put("uid", uid);
                            //查询评论和分数
                            String sql_search = "select * from result where uid=? and lab=?";
                            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid, lab);
                            //无记录status为0
                            if (result_list.size() == 0) {
                                map.put("status", 0);
                                map.put("score", null);
                                map.put("comment", null);
                                map.put("is_release", null);
                            } else {
                                map.put("status", 1);
                                map.put("score", result_list.get(0).get("score"));
                                map.put("comment", result_list.get(0).get("comment"));
                                map.put("is_release", result_list.get(0).get("is_release"));
                            }
                            list.add(map);
                        }
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
        if (AuthUtil.notTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String dir = pdfBasePath + "submitted/";
            try {
                String uid = req.get("uid").toString();
                String lab = req.get("lab").toString();
                String filename = uid + lab + ".pdf";
                File file = new File(dir + filename);
                PdfUtil.downloadFile(file, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取大实验、小实验下的所有问题
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    public List<Map<String, Object>> getQuestions(@RequestBody Map<String, String> req, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String lab = req.get("lab");
            String lower_lab = req.get("lower_lab");
            String sql = "select step, question_id, question from question where lab=? and lower_lab=?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, lab, lower_lab);
            if (result.size() == 0) {
                throw new ResourceNotFoundException();
            } else {
                return result;
            }
        }
    }

    //批量下载pdf
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/downloadPDFs", method = RequestMethod.POST)
    public void downloadPDFs(@RequestBody String[] filenames, HttpSession session, HttpServletResponse response) throws IOException {
        if (AuthUtil.notTeacherLogin(session)) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String dir = pdfBasePath + "submitted/";
            //存放压缩文件的目标
            File dest = new File(pdfBasePath + new Date().getTime() + ".zip");
            //生成压缩文件
            PdfUtil.generateZip(new File(dir), dest, filenames);
            //传输文件
            PdfUtil.downloadFile(dest, response);
            //删除临时文件
            if (!dest.delete())
                throw new IOException("temporary zip file cannot delete");
        }
    }
}