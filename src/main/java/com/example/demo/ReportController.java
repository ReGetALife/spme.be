package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db")
public class ReportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.OK)
    public class SqlOKException extends RuntimeException{}

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public List<Map<String, Object>> getStudents(HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            throw new ResourceNotFoundException();
        } else {
            String sql = "select * from student";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            return list;
        }
    }

    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/getStudent/{uid}", method = RequestMethod.GET)
    public List<Map<String, Object>> getStudentByID(@PathVariable String id, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql = "select * from student where uid='" + id + "'";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            if(list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }

    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/subScore", method = RequestMethod.POST)
    public List<Map<String, Object>> subScores(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = req.get("uid");
            String lab = req.get("lab");
            String step = req.get("step");
            String score = req.get("score");
            String sql = "update report set score='" + score + "' where uid='" + uid + "' and lab='" + lab + "' and step='" + step + "';";
            jdbcTemplate.execute(sql);
            throw new SqlOKException();
        }
    }

    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/getreport/{uid}", method = RequestMethod.GET)
    public List<Map<String, Object>> getReport(@PathVariable String uid, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql = "select * from report where uid='" + uid + "';";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            if(list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }

    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public List<Map<String, Object>> getQuestions(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String lab = req.get("lab");
            String step = req.get("step");
            String sql = "select question,answer,uid from report where lab='" + lab + "' and step='" + step + "';";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            if(list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }

    
}
