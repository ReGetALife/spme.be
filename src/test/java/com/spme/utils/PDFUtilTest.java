package com.spme.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PDFUtilTest {

    @Test
    public void generateZip() throws IOException {
        File dir = new File("./report/submitted");
        File dest = new File("./report/" + new Date().getTime() + ".zip");
        String[] files = {"ST021RACF.pdf", "ST007RACF.pdf", "ST022RACF.pdf"};
        if (!dir.exists() && !dir.mkdirs())
            return;
        PDFUtil.generateZip(dir, dest, files);
    }
}