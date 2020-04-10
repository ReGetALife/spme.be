package com.spme.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * PDF相关的操作
 *
 * @author 李庆国
 * @author 徐仁和
 */
public class PDFUtil {

    /**
     * Generate lab report of a certain uid
      * @param uid uid
     * @param lab lab name of this report
     * @param path path to store generated report
     * @param template jdbc template
     */
    public static void generatePDF(String uid, String lab, String path, JdbcTemplate template) {
        String sql = "select * from question natural left join (select * from report where uid=?) as r where lab=? order by lower_lab, step, question_id";
        List<Map<String, Object>> questionsWithAnswer = template.queryForList(sql, uid, lab);
        // resolve data
        Map<Integer, Map<Integer, Map<Integer, String[]>>> subLabs = new TreeMap<>();
        for (Map<String, Object> q : questionsWithAnswer) {
            Integer subLab = Integer.valueOf(q.get("lower_lab").toString());
            Integer step = Integer.valueOf(q.get("step").toString());
            Integer question = Integer.valueOf(q.get("question_id").toString());
            if (!subLabs.containsKey(subLab)) {
                subLabs.put(subLab, new TreeMap<>());
            }
            Map<Integer, Map<Integer, String[]>> steps = subLabs.get(subLab);
            if (!steps.containsKey(step)) {
                steps.put(step, new TreeMap<>());
            }
            Map<Integer, String[]> qs = steps.get(step);
            String answer = q.get("answer") == null ? "" : q.get("answer").toString();
            qs.put(question, new String[]{q.get("question").toString(), answer});
        }
        // write data
        try {
            Document document = new Document();
            // available fonts in text-asian package can be found in itext-asian-5.2.0.jar!/com/itextpdf/text/pdf/fonts/cmaps/cjk_registry.properties
            // ref: https://blog.csdn.net/weixin_41807385/article/details/98478061
            BaseFont font = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font cf_lower_lab = new Font(font, 16, Font.BOLD);
            Font cf_step = new Font(font, 12, Font.BOLD);
            Font cf_question = new Font(font, 8, Font.BOLD);
            Font cf_answer = new Font(font, 8, Font.NORMAL);
            File dir = new File(path);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException();
            }
            File file = new File(dir, uid + lab + ".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            // write uid and time
            Paragraph uidAndTime = new Paragraph(uid + "    "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            uidAndTime.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(uidAndTime);
            // write lab name
            Paragraph pLab = new Paragraph(lab, FontFactory.getFont(FontFactory.HELVETICA, 24,
                    Font.BOLD, new CMYKColor(0, 255, 255, 17)));
            document.add(pLab);
            for (Map.Entry<Integer, Map<Integer, Map<Integer, String[]>>> subLabEntry : subLabs.entrySet()) {
                // write sub lab name
                document.add(new Paragraph("\n实验" + subLabEntry.getKey(), cf_lower_lab));
                for (Map.Entry<Integer, Map<Integer, String[]>> stepEntry : subLabEntry.getValue().entrySet()) {
                    // write step name
                    document.add(new Paragraph("\n步骤" + stepEntry.getKey(), cf_step));
                    for (Map.Entry<Integer, String[]> questionEntry : stepEntry.getValue().entrySet()) {
                        document.add(new Paragraph("题目" + questionEntry.getKey() + "    " + questionEntry.getValue()[0], cf_question));
                        document.add(new Paragraph(questionEntry.getValue()[1] + "\n", cf_answer));
                    }
                }
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //下载单个pdf/文件
    public static void downloadFile(File file, HttpServletResponse response) throws IOException {
        if (file.exists()) {
            FileInputStream ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            ServletOutputStream out = response.getOutputStream();
            //读取文件流
            int len;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
            ips.close();
        }
    }

    //批量下载pdf
    public static void generateZip(File baseDir, File dest, String[] filenames) throws IOException {
        //存储字节流的容器
        byte[] container = new byte[1024 * 10];
        //文件输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));
        for (String file : filenames) {
            File pdf = new File(baseDir, file);
            if (pdf.exists() && pdf.isFile()) {
                ZipEntry zipEntry = new ZipEntry(file);
                //加入一个zip条目
                zipOutputStream.putNextEntry(zipEntry);
                //pdf文件输入字节流
                InputStream inputStream = new BufferedInputStream(new FileInputStream(pdf));
                while (inputStream.read(container) != -1) {
                    zipOutputStream.write(container);
                }
                inputStream.close();
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
            }
        }
        zipOutputStream.close();
    }
}
