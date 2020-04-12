package com.spme.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @author Qingguo Li
 */
@Service
public class CmdService {

    @Resource
    JclService js;

    /**
     * run tso commands and get response output
     */
    public String runCMD(Map<String, String> commBody, HttpSession session) {
        String command = "";
        if (commBody.get("cmd") != null && !commBody.get("cmd").equals("")) {
            command = commBody.get("cmd");
        }
        // split command
        String[] cmds = command.split(";");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cmds.length; i++) {
            // format command
            cmds[i] = cmds[i].replaceAll("[ \n\r]+", " ");
            cmds[i] = cmds[i].trim();
            // handle command too long
            if (cmds[i].length() > 72) {
                cmds[i] = cmds[i].replaceAll(" ", " -\n");
            }
            if (cmds[i].length() == 0) {
                continue;
            }
            if (i == cmds.length - 1) {
                sb.append(cmds[i]);
            } else {
                sb.append(cmds[i]).append("\n");
            }
        }
        command = sb.toString();

        //添加body中的text
        String line1 = "//SPMECMD JOB CLASS=A,MSGLEVEL=(1,1),MSGCLASS=H,";
        String line2 = "// TIME=2                                       ";
        String line3 = "//SEND EXEC PGM=IKJEFT01                        ";
        String line4 = "//SYSPRINT DD DUMMY                             ";
        String line5 = "//SYSTSPRT DD SYSOUT=*                          ";
        String line6 = "//SYSTSIN  DD *                                 ";
        String line7 = command;
        String line8 = "/*                                              ";
        String allLines = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", line1, line2, line3, line4, line5, line6, line7, line8);

        // 102 is id of sys print
        return js.submitJCL(session, allLines, 102);
    }

}
