package com.spme.service;

import com.spme.domain.*;
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

    /**
     * Define data class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAD1)
     */
    public String createDataClass(HttpSession session, DataClass dataClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAD1 DEFINE +\n" +
                    fieldsResolver(dataClass) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Define storage class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAS1)
     */
    public String createStorageClass(HttpSession session, StorageClass storageClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAS1 DEFINE +\n" +
                    fieldsResolver(storageClass) +
                    ")\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Define management class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ1)
     */
    public String createManagementClass(HttpSession session, ManagementClass managementClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            // add quotes to avoid prefix
            managementClass.setScds("'" + managementClass.getScds() + "'");
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//TEMPFILE  DD  DSN=&&TEMPFILE,DISP=(MOD,PASS),\n" +
                    "//  SPACE=(TRK,(1,1)),LRECL=300,RECFM=F,BLKSIZE=300\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAJ1 DEFINE +\n" +
                    fieldsResolver(managementClass) +
                    ")\n" +
                    "/*\n" +
                    "//STEP2   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD DSN=&&TEMPFILE,DISP=(OLD,DELETE,DELETE)\n" +
                    "/*";
            return js.submitJCL(session, jcl, 108);
        }
        return "";
    }

    /**
     * Define storage group of pool type
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ2)
     */
    public String createPoolStorageGroup(HttpSession session, PoolStorageGroup poolStorageGroup) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            poolStorageGroup.setScds("'" + poolStorageGroup.getScds() + "'");
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAJ2 DEFINE +\n" +
                    fieldsResolver(poolStorageGroup) +
                    ")\n" +
                    "/*\n" +
                    "//TEMPFILE  DD  DSN=&&TEMPFILE,DISP=(MOD,PASS),\n" +
                    "//  SPACE=(TRK,(1,1)),LRECL=300,RECFM=F,BLKSIZE=300\n" +
                    "//STEP2   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD DSN=&&TEMPFILE,DISP=(OLD,DELETE,DELETE)\n" +
                    "/*";
            return js.submitJCL(session, jcl, 108);
        }
        return "";
    }

    /**
     * Add volume for storage group
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIB)
     */
    public String addVolume(HttpSession session, StorageGroupVolume volume) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            volume.setScds("'" + volume.getScds() + "'");
            if(volume.getStatus().equals("")) {
                // it is actually required
                volume.setStatus("ENABLE");
            }
            String jcl = getHead(uid) +
                    "//ADDVOL1 EXEC ACBJBAOB,\n" +
                    "//        PLIB1='SYS1.DGTPLIB',\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//TEMPFILE  DD  DSN=&&VOLADDS,DISP=(NEW,KEEP),\n" +
                    "//  SPACE=(TRK,(1,1)),LRECL=300,RECFM=F,BLKSIZE=300\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAI9) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//VOLADD  DD  *\n" +
                    fieldsResolver(volume) +
                    "\n" +
                    "/*\n" +
                    "//ADDVOL2 EXEC ACBJBAOB,\n" +
                    "//        PLIB1='SYS1.DGTPLIB',\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD DSN=&&VOLADDS,DISP=(OLD,DELETE)\n";
            return js.submitJCL(session, jcl, 109);
        }
        return "";
    }

    /**
     * Translate ACS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAC2)
     */
    public String translateACS(HttpSession session, AcsTranslate acsTranslate) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//TRANSLAT EXEC ACBJBAOB,\n" +
                    "//         PLIB1='SYS1.DGTPLIB',\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "DEL " + acsTranslate.getListname() + "\n" +
                    "ISPSTART CMD(ACBQBAO1 +\n" +
                    fieldsResolver(acsTranslate) +
                    ") +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//TRANGEN  EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD  DSN=" + acsTranslate.getListname() + ",DISP=SHR\n" +
                    "//SYSUT2   DD  SYSOUT=*\n" +
                    "//SYSIN    DD  DUMMY\n" +
                    "//SYSPRINT DD  SYSOUT=*\n";
            return js.submitJCL(session, jcl, 106);
        }
        return "";
    }

    /**
     * Validate ACS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAC2)
     */
    public String validateACS(HttpSession session, AcsValidate acsValidate) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//VALIDAT EXEC ACBJBAOB,\n" +
                    "//         PLIB1='SYS1.DGTPLIB',\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "DEL " + acsValidate.getListname() + "\n" +
                    "ISPSTART CMD(ACBQBAO2 +\n" +
                    fieldsResolver(acsValidate) +
                    ") +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//VALGEN  EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD  DSN=" + acsValidate.getListname() + ",DISP=SHR\n" +
                    "//SYSUT2   DD  SYSOUT=*\n" +
                    "//SYSIN    DD  DUMMY\n" +
                    "//SYSPRINT DD  SYSOUT=*\n";
            return js.submitJCL(session, jcl, 106);
        }
        return "";
    }

    /**
     * Test ACS
     * Sample JCL: SYS1.SACBCNTL(ACBJBAC2)
     */
    public String testACS(HttpSession session, AcsTest acsTest) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//TEST EXEC ACBJBAOB,\n" +
                    "//         PLIB1='SYS1.DGTPLIB',\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "DEL " + acsTest.getListname() + "\n" +
                    "ISPSTART CMD(ACBQBAIA +\n" +
                    fieldsResolver(acsTest) +
                    ") +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//TSTGEN  EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD  DSN=" + acsTest.getListname() + ",DISP=SHR\n" +
                    "//SYSUT2   DD  SYSOUT=*\n" +
                    "//SYSIN    DD  DUMMY\n" +
                    "//SYSPRINT DD  SYSOUT=*\n";
            return js.submitJCL(session, jcl, 106);
        }
        return "";
    }
}
