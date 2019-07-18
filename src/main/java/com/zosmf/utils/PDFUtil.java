package com.zosmf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.jdbc.core.JdbcTemplate;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PDFUtil {

    //生成某个学生的的实验报告的pdf
    public static void generatePDF(String uid, String lab, String path, JdbcTemplate template) {
        String sql = "select * from report natural join question where uid=? and lab=? order by lower_lab, step, question_id";
        List<Map<String, Object>> list = template.queryForList(sql, uid, lab);
        if (list.size() != 0) {
            Document document = new Document();
//                BaseFont bfChinese = null;
            try {
                BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                Font cf_lower_lab = new Font(font, 16, Font.BOLD);
                Font cf_step = new Font(font, 12, Font.BOLD);
                Font cf_question = new Font(font, 8, Font.BOLD);
                Font cf_answer = new Font(font, 8, Font.NORMAL);
                //创建保存目录
                File dir = new File(path);
                if (!dir.exists() && !dir.mkdirs()) {
                    throw new IOException();
                }
                File file = new File(dir, uid + lab + ".pdf");
                PdfWriter.getInstance(document, new FileOutputStream(file));
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
                for (Map<String, Object> m : list) {
                    if (currentLab != m.get("lower_lab")) {
                        title_lower = new Paragraph("实验" + m.get("lower_lab"), cf_lower_lab);
                        if (section_lower != chapter1.addSection("")) {
                            document.add(section_lower);
                        }
                        section_lower = chapter1.addSection(title_lower);
                        section_lower.setNumberDepth(0);
                        currentLab = m.get("lower_lab");
                    }
                    if (currentStep != m.get("step")) {
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
            } catch (DocumentException | IOException de) {
                de.printStackTrace();
            }
        }

    }
}
