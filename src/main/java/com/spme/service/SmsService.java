package com.spme.service;

import com.spme.domain.BaseConfiguration;
import com.spme.domain.DatasetInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Storage administration tasks that are performed using ISMF options can also be done in batch with JCL, CLISTs,
 * and REXX EXECs that are provided by NaviQuest.
 * Sample JCL can be found in the SYS1.SACBCNTL library
 * z/OS version: V2R1
 * Ref: https://www.ibm.com/support/knowledgecenter/SSLTBW_2.1.0/com.ibm.zos.v2r1.idas200/job.htm
 *
 * @author Qingguo Li
 */
@Service
public class SmsService {

    @Resource
    private DatasetService ds;

    @Resource
    private JclService js;

    /**
     * ACBJBAOB needs dataset TABL2='userid.TEST.ISPTABL' for saving ismf tables;
     * we should allocate this data set with the same DCB parameters as the ISMF
     * DGTTLIB dataset(SYS1.DGTTLIB); table can be large - allocate a large data set
     */
    private boolean prepareTable2(HttpSession session) {
        String table2 = session.getAttribute("ZOSMF_Account") + ".TEST.ISPTABL";
        List<Map<String, String>> res = ds.getDatasetList(session, table2);
        for (Map<String, String> dataset : res) {
            if (dataset.get("dsname").equals(table2)) {
                return true;
            }
        }
        // not found, allocate one
        DatasetInfo datasetInfo = new DatasetInfo();
        datasetInfo.setDsname(table2);
        datasetInfo.setBlksize(27920);
        datasetInfo.setDirblk(10);
        datasetInfo.setLrecl(80);
        datasetInfo.setPrimary(11);
        datasetInfo.setSecondary(1);
        datasetInfo.setRecfm("FB");
        return ds.createDataset(session, datasetInfo);
    }

    /**
     * get head of JCL, containing job card and jcl lib statement
     */
    private static String getHead(String uid) {
        return "//" + uid + " JOB (ACCT),'" + uid + "',MSGCLASS=H,\n" +
                "//      CLASS=A,MSGLEVEL=(1,1),TIME=(0,10)\n" +
                "//MYLIB JCLLIB ORDER=SYS1.SACBCNTL\n";
    }

    /**
     * resolve object fields to jcl parameters
     */
    private static String fieldsResolver(Object o) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object value = f.get(o);
                if (value != null && !value.toString().equals("")) {
                    sb.append(f.getName().toUpperCase()).append("(").append(value.toString()).append(") +\n");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * Display base configuration of a SCDS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAB1)
     */
    public String getBaseConfig(HttpSession session, String scds) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//DISPLAY   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAB1 DISPLAY +\n" +
                    "SCDS(" + scds + ") +\n" +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Alter base configuration of a SCDS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAB1)
     */
    public String createBaseConfig(HttpSession session, BaseConfiguration config) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAB1 DEFINE +\n" +
                    fieldsResolver(config) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Create base configuration of a SCDS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAB1)
     */
    public String alterBaseConfig(HttpSession session, BaseConfiguration config) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAB1 ALTER +\n" +
                    fieldsResolver(config) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }
}
