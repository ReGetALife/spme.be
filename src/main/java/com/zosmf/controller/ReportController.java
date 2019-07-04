package com.zosmf.controller;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db")
public class ReportController {

    private String pdfBasePath = "/home/user/Documents/report/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizedException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException{}

    @ResponseStatus(value = HttpStatus.OK)
    public class SqlOKException extends RuntimeException{}

    //获取所有学生
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public List<Map<String, Object>> getStudents(HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new ResourceNotFoundException();
        } else {
            String sql = "select * from student";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            return list;
        }
    }

    //根据主机号获取学生 参数为要查找的学生主机号
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/getStudent", method = RequestMethod.POST)
    public List<Map<String, Object>> getStudentByID(@RequestBody String uid, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        System.out.println(ZOSMF_Account);
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql = "select * from student where uid=?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid);
            if(list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                return list;
            }
        }
    }

    //老师提交分数和评论
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/subScore", method = RequestMethod.POST)
    public List<Map<String, Object>> subScores(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object is_teacher = session.getAttribute("is_teacher");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null|| is_teacher != "yes") {
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
            if(result_list.size() == 0){
                String sql_insert = "insert into result (uid, score, comment, lab) values ( ?, ?, ?, ?);";
                jdbcTemplate.update(sql_insert, uid, score, comment, lab);
            }else{
                int tag = (int) result_list.get(0).get("is_release");
                if (tag == 1){
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
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/reScore", method = RequestMethod.POST)
    public List<Map<String, Object>> releaseScore(HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object is_teacher = session.getAttribute("is_teacher");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null|| is_teacher != "yes") {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String sql_search = "select * from result";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search);
            if (result_list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                String sql_update = "update result set is_release=1";
                jdbcTemplate.update(sql_update);
            }
            throw new SqlOKException();
        }
    }

    //学生查看发布的成绩，未发布时返回返回ResourseNotFound的异常
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/checkScore", method = RequestMethod.GET)
    public List<Map<String, Object>> checkScore(HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = ZOSMF_Account.toString();
            //String uid = "ST009";
            System.out.println(uid);
            String sql_search = "select * from result where uid=?";
            List<Map<String, Object>> result_list = jdbcTemplate.queryForList(sql_search, uid);
            List<Map<String, Object>> result_return = new ArrayList();
            System.out.println(result_list);
            if (result_list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                for (int i = 0; i < result_list.size(); i++){
                    if (result_list.get(i).get("is_release").equals(1)){
                        result_return.add(result_list.get(i));
                    }
                }
            }
            if (result_return == null){
                throw new ResourceNotFoundException();
            } else {
                return result_return;
            }
        }
    }

    //学生生成自己的实验报告
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/saveReports", method = RequestMethod.POST)
    public List<Map<String, Object>> saveReport(@RequestBody Map<String, String> req, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String uid = ZOSMF_Account.toString();
            //String uid = req.get("uid");
            String lab = req.get("lab");
            String sql = "select * from report natural join question where uid=? and lab=? order by lower_lab, step, question_id";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, uid, lab);
            if(list.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                Document document = new Document();
                BaseFont bfChinese = null;
                try{
                    BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
                    Font cf_lower_lab = new Font(font, 16, Font.BOLD);
                    Font cf_step = new Font(font, 12, Font.BOLD);
                    Font cf_question = new Font(font, 8, Font.BOLD);
                    Font cf_answer = new Font(font, 8, Font.NORMAL);
                    PdfWriter.getInstance(document, new FileOutputStream(pdfBasePath + uid + lab + ".pdf"));
                    document.open();
                    Paragraph title = new Paragraph(lab, FontFactory.getFont(FontFactory.HELVETICA, 24,
                            Font.BOLD, new CMYKColor(0, 255, 255, 17)));
                    Chapter chapter1 = new Chapter(title, 1);
                    chapter1.setNumberDepth(0);
                    document.add(chapter1);
                    Paragraph title_lower;
                    Section section_lower = chapter1.addSection("");
                    section_lower.setNumberDepth(0);
                    Object currentLab = 0, currentStep = 0;
                    for(Map<String, Object> m : list){
                        if(currentLab != m.get("lower_lab")){
                            title_lower = new Paragraph("实验" + m.get("lower_lab"), cf_lower_lab);
                            if(section_lower !=chapter1.addSection("")){
                                document.add(section_lower);
                            }
                            section_lower = chapter1.addSection(title_lower);
                            section_lower.setNumberDepth(0);
                            currentLab = m.get("lower_lab");
                        }
                        if(currentStep != m.get("step")){
                            title_lower = new Paragraph("步骤" + m.get("step"), cf_step);
                            //title_lower.setFont(font);
                            section_lower.add(title_lower);
                            currentStep = m.get("step");
                        }
                        title_lower = new Paragraph("题目" + m.get("question_id") + "  " + m.get("question"), cf_question);
                        section_lower.add(title_lower);
                        title_lower = new Paragraph("回答:" + m.get("answer"), cf_answer);
                        section_lower.add(title_lower);
                    }
                    document.add(section_lower);
                    document.close();
                    //File file = new File("D:\\" + uid + lab + ".pdf");
                    //file.delete();
                }catch (DocumentException de){
                    de.printStackTrace();
                }catch (FileNotFoundException fnfe){
                    fnfe.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                throw new SqlOKException();
            }
        }
    }

    //根据主机账号和实验名预览pdf
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/getReports", method = RequestMethod.POST)
    public String viewPDF(@RequestBody Map<String, String> req, HttpSession session) throws IOException{
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }else{
            String uid = req.get("uid");
            String lab = req.get("lab");
            //String retString = null;
            //这里改成服务器上的pdf文件夹路径
            //String dir = "D:\\";
            String filename = uid + lab + ".pdf";
            //File file = new File(dir + filename);
            //这里的 D: 改成服务器地址
            //String path = "D:" +  dir.substring(dir.lastIndexOf('\\')) + filename;
            //retString = path.replaceAll("\\\\", "/");
            //return retString;
            return pdfBasePath+filename;
        }
    }

    //提供给老师的接口，批量导入students
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/saveStudents", method = RequestMethod.POST)
    public String saveStudents(@RequestBody List<Map<String, String>> reqs, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object is_teacher = session.getAttribute("is_teacher");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null || is_teacher != "yes") {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }else{
        for(Map<String, String> req : reqs){
            String uid = req.get("uid");
            String sid = req.get("sid");
            String sname = req.get("name");
            String sql = "insert into student (uid, sid, name) values (?, ?, ?);";
            jdbcTemplate.update(sql, uid, sid, sname);
        }
        throw new SqlOKException();
        }
    }

    //计算某个实验的提交人数
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/subCount", method = RequestMethod.POST)
    public int count(@RequestBody String lab, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object is_teacher = session.getAttribute("is_teacher");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null || is_teacher != "yes") {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }else{
            int count = 0;
            //改成服务器pdf的文件夹地址
            //String dir = "D:\\";
            String dir = pdfBasePath;
            File file_dir = new File(dir);
            File[] files = file_dir.listFiles();
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile()){
                    String filename = files[i].getName();
                    if(filename.indexOf(lab) != -1){
                        count++;
                    }
                }
            }
            return count;
        }
    }

    //下载单个pdf，需要主机账号和实验名
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/downloadPDFs", method = RequestMethod.POST)
    public void downloadPDFs(@RequestBody Map<String, Object> req, HttpSession session, HttpServletResponse response){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object is_teacher = session.getAttribute("is_teacher");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null || is_teacher != "yes") {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        }else{
            ServletOutputStream out = null;
            FileInputStream ips = null;
            //改成服务器上的pdf文件夹路径
            //String dir = "D:\\";
            String dir = pdfBasePath;
            try{
                String uid = req.get("uid").toString();
                String lab = req.get("lab").toString();
                String filename = uid + lab + ".pdf";
                File file = new File(dir + filename);
                if(!file.exists()){
                    return;
                }else {
                    ips = new FileInputStream(file);
                    response.setContentType("multipart/form-data");
                    response.addHeader("Content-Disposition", "attachment; filename=\"" + uid + lab + ".pdf" + "\"");
                    out = response.getOutputStream();
                    //读取文件流
                    int len = 0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((len = ips.read(buffer)) != -1){
                        out.write(buffer,0,len);
                    }
                    out.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    out.close();
                    ips.close();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

    //获取大实验、小实验、步骤下的所有问题
    @CrossOrigin(origins="*", allowCredentials = "true")
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    public List<Map<String, Object>> getQuestions(@RequestBody Map<String, String> req, HttpSession session) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            //没有token信息，授权失败
            throw new UnauthorizedException();
        } else {
            String lab = req.get("lab");
            String step = req.get("step");
            String lower_lab = req.get("lower_lab");
            String sql = "select question_id, question from question where lab=? and step=? and lower_lab=?";
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, lab, step, lower_lab);
            if (result.size() == 0){
                throw new ResourceNotFoundException();
            } else {
                return result;
            }
        }
    }
}
