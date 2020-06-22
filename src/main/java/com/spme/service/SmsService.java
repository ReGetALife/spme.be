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
     * Display data class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAD1)
     */
    public String displayDataClass(HttpSession session, DataClass dataClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP2   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAD1 DISPLAY +\n" +
                    fieldsResolver(dataClass) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Alter data class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAD1)
     */
    public String alterDataClass(HttpSession session, DataClass dataClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP2   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAD1 ALTER +\n" +
                    fieldsResolver(dataClass) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * List management class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIO)
     */
    public String listManagementClass(HttpSession session, ManagementClass managementClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//GENMCLST EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD    *\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAID SAVE MCNAMES +\n" +
                    "SCDS("+ managementClass.getScds() +") MGMTCLAS("+ managementClass.getMgmtclas() +")) +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//DELREPDS EXEC  PGM=IDCAMS\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    " DELETE MCNAMS.REPORT\n" +
                    "/*\n" +
                    "//ALCISPFL EXEC  PGM=IEFBR14\n" +
                    "//ISPFILE  DD    DSN=MCNAMS.REPORT,DISP=(NEW,CATLG),\n" +
                    "//         BLKSIZE=0,SPACE=(TRK,(3,1)),RECFM=FBA,LRECL=133,UNIT=SYSDA\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    "/*\n" +
                    "//GENMCREP EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//ISPFILE  DD    DSN=MCNAMS.REPORT,DISP=OLD\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBARD MCNAMES) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//SYSIN    DD    *\n" +
                    "MGMTCLAS\n" +
                    "EXPNONUSE\n" +
                    "EXPDATE\n" +
                    "RETLMIT\n" +
                    "PARTIALREL\n" +
                    "PRIMDAYS\n" +
                    "L1DAYS\n" +
                    "MIGTYPE\n" +
                    "NOGDGPRI\n" +
                    "RLDOFGDSAC\n" +
                    "BKPFREQ\n" +
                    "NOBKPSDSE\n" +
                    "NOBKPSDSD\n" +
                    "TITLE=STATUS OF MANAGEMENT CLASSES AS ON 07/01/12\n" +
                    "/*\n" +
                    "//CPYMCREP EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD    DSN=MCNAMS.REPORT,DISP=SHR\n" +
                    "//SYSUT2   DD    SYSOUT=*\n" +
                    "//SYSIN    DD    DUMMY\n" +
                    "//SYSPRINT DD    SYSOUT=*\n";
            return js.submitJCL(session, jcl, 117);
        }
        return "";
    }

    /**
     * List data class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIL)
     */
    public String listDataClass(HttpSession session, DataClass dataClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//GENDCLST EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAIC SAVE DCNAMES +\n" +
                    "SCDS("+ dataClass.getScds() +") DATACLAS("+ dataClass.getDcname() +")) +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//DELREPDS EXEC  PGM=IDCAMS\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    " DELETE DCNAMS.REPORT\n" +
                    "/*\n" +
                    "//ALCISPFL EXEC  PGM=IEFBR14\n" +
                    "//ISPFILE  DD    DSN=DCNAMS.REPORT,DISP=(NEW,CATLG),\n" +
                    "//         BLKSIZE=0,SPACE=(TRK,(3,1)),RECFM=FBA,LRECL=133,UNIT=SYSDA\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    "/*\n" +
                    "//GENDCREP EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//ISPFILE  DD    DSN=DCNAMS.REPORT,DISP=OLD\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBARB DCNAMES) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//SYSIN    DD    *\n" +
                    "DATACLAS\n" +
                    "RECORG\n" +
                    "RECFM\n" +
                    "LRECL\n" +
                    "KEYLEN\n" +
                    "KEYOFF\n" +
                    "AVGREC\n" +
                    "AVGVL\n" +
                    "SPCEPRI\n" +
                    "SPACESEC\n" +
                    "SPACEDIR\n" +
                    "EXPDATE\n" +
                    "VOLCNT\n" +
                    "ADDVOLAMNT\n" +
                    "MAXVOL\n" +
                    "SMBVSP\n" +
                    "FRLOG\n" +
                    "RLSCF\n" +
                    "PEFSCLG\n" +
                    "PERFSEG\n" +
                    "OVERRIDE\n" +
                    "SDB\n" +
                    "EATTR\n" +
                    "RECLAIMCA\n" +
                    "LOGREPL\n" +
                    "RMODE31\n" +
                    "TITLE=STATUS OF DATA CLASSES  AS ON 12/01/12\n" +
                    "//CPYDCREP EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD    DSN=DCNAMS.REPORT,DISP=SHR\n" +
                    "//SYSUT2   DD    SYSOUT=*\n" +
                    "//SYSIN    DD    DUMMY\n" +
                    "//SYSPRINT DD    SYSOUT=*\n";
            return js.submitJCL(session, jcl, 116);
        }
        return "";
    }

    /**
     * List storage class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIU)
     */
    public String listStorageClass(HttpSession session, StorageClass storageClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//GENSCLST EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=ST027.TEST.ISPTABL\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAIF SAVE SCNAMES +\n" +
                    "SCDS(ST027.SMS.SCDS) STORCLAS(SCTEST)) +\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//DELREPDS EXEC  PGM=IDCAMS\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    " DELETE SCNAMS.REPORT\n" +
                    "/*\n" +
                    "//ALCISPFL EXEC  PGM=IEFBR14\n" +
                    "//ISPFILE  DD    DSN=SCNAMS.REPORT,DISP=(NEW,CATLG),\n" +
                    "//         BLKSIZE=0,SPACE=(TRK,(3,1)),RECFM=FBA,LRECL=133,UNIT=SYSDA\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    "/*\n" +
                    "//GENSCREP EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=ST027.TEST.ISPTABL\n" +
                    "//ISPFILE  DD    DSN=SCNAMS.REPORT,DISP=OLD\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBARH SCNAMES) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//SYSIN    DD    *\n" +
                    "STORCLAS\n" +
                    "DIRRESP\n" +
                    "DBIS\n" +
                    "SEQRESP\n" +
                    "SBIS\n" +
                    "AVALBLTY\n" +
                    "GRNTSPC\n" +
                    "LSTMDUID\n" +
                    "LSTMODDATE\n" +
                    "LSTMDTIME\n" +
                    "GRNTSNCWR\n" +
                    "INACCRSP\n" +
                    "MULTITSG\n" +
                    "PAVCAP\n" +
                    "OAMLVL\n" +
                    "DISCSPHERE\n" +
                    "TITLE=STATUS OF STORAGE CLASSES  AS ON 11/01/11\n" +
                    "//CPYSCREP EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD    DSN=SCNAMS.REPORT,DISP=SHR\n" +
                    "//SYSUT2   DD    SYSOUT=*\n" +
                    "//SYSIN    DD    DUMMY\n" +
                    "//SYSPRINT DD    SYSOUT=* \n";
            return js.submitJCL(session, jcl, 116);
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
     * Display management class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ1)
     */
    public String displayManagementClass(HttpSession session, ManagementClass managementClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            // add quotes to avoid prefix
            //managementClass.setScds("'" + managementClass.getScds() + "'");
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAJ1 DISPLAY +\n" +
                    "SCDS("+ managementClass.getScds() +") +\n" +
                    "MGMTCLAS("+ managementClass.getMgmtclas() +") +\n" +
                    ")\n" +
                    "/*";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Alter management class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ1)
     */
    public String alterManagementClass(HttpSession session, ManagementClass managementClass) {
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
                    "ISPSTART CMD(ACBQBAJ1 ALTER +\n" +
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
     * Display storage class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAS1)
     */
    public String displayStorageClass(HttpSession session, StorageClass storageClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP2   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAS1 DISPLAY +\n" +
//                    "SCDS(" + uid + ".SMS.SCDS) +\n" +
//                    "DCNAME(DCSDS) +\n" +
                    fieldsResolver(storageClass) +
                    ") +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(999999)\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * Alter storage class
     * Sample JCL: SYS1.SACBCNTL(ACBJBAS1)
     */
    public String alterStorageClass(HttpSession session, StorageClass storageClass) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAS1 ALTER +\n" +
                    fieldsResolver(storageClass) +
                    ")\n" +
                    "/*\n";
            return js.submitJCL(session, jcl, 104);
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
     * Display storage group of pool type
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ2)
     */
    public String displayPoolStorageGroup(HttpSession session, PoolStorageGroup poolStorageGroup) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            poolStorageGroup.setScds("'" + poolStorageGroup.getScds() + "'");
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE PREFIX(IBMUSER)\n" +
                    "ISPSTART CMD(ACBQBAJ2 DISPLAY +\n" +
                    "SCDS(" + poolStorageGroup.getScds() + ") +\n" +
                    "STORGRP(" + poolStorageGroup.getStorgrp() + ") +\n" +
                    ")\n" +
                    "/*";
            return js.submitJCL(session, jcl, 104);
        }
        return "";
    }

    /**
     * List storage group of pool type
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIX)
     */
    public String listPoolStorageGroup(HttpSession session, PoolStorageGroup poolStorageGroup) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            String jcl = getHead(uid) +
                    "//GENSGLST EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAIG SAVE SGNAMES +\n" +
                    "SCDS(" + poolStorageGroup.getScds() + ") STORGRP(" + poolStorageGroup.getStorgrp() + ") STGTYPE(POOL) SPACEGB(N))+\n" +
                    "NEWAPPL(DGT) BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//DELREPDS EXEC  PGM=IDCAMS\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    " DELETE SGNAMS.REPORT\n" +
                    "/*\n" +
                    "//ALCISPFL EXEC  PGM=IEFBR14\n" +
                    "//ISPFILE  DD    DSN=SGNAMS.REPORT,DISP=(NEW,CATLG),\n" +
                    "//         BLKSIZE=0,SPACE=(TRK,(3,1)),RECFM=FBA,LRECL=133,UNIT=SYSDA\n" +
                    "//SYSPRINT DD    SYSOUT=*\n" +
                    "//SYSIN    DD    *\n" +
                    "/*\n" +
                    "//GENSGREP EXEC  ACBJBAOB,\n" +
                    "//         PLIB1=SYS1.DGTPLIB,\n" +
                    "//         TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//ISPFILE  DD    DSN=SGNAMS.REPORT,DISP=OLD\n" +
                    "//SYSTSIN  DD    *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBARJ SGNAMES) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//SYSIN    DD    *\n" +
                    "STORGRP\n" +
                    "SGTYPE\n" +
                    "VIOMXSZ\n" +
                    "VIOU\n" +
                    "AUTOMIG\n" +
                    "MIGSYS\n" +
                    "ABCK\n" +
                    "BKPSYS\n" +
                    "ADMP\n" +
                    "DUMPSYS\n" +
                    "OVRFLOW\n" +
                    "EXTSGNM\n" +
                    "CPBSGN\n" +
                    "MIGH\n" +
                    "MIGL\n" +
                    "TOTALSPC\n" +
                    "FREESPC\n" +
                    "PERFSP\n" +
                    "BREAKPT\n" +
                    "TRKHITHRS\n" +
                    "TRKLOWTHRS\n" +
                    "OAMDELPRO\n" +
                    "OAMRETPRO\n" +
                    "PROCPRIOR\n" +
                    "TITLE=STATUS OF STORAGE GROUP  AS ON 13/01/10\n" +
                    "//CPYSGREP EXEC  PGM=IEBGENER\n" +
                    "//SYSUT1   DD    DSN=SGNAMS.REPORT,DISP=SHR\n" +
                    "//SYSUT2   DD    SYSOUT=*\n" +
                    "//SYSIN    DD    DUMMY\n" +
                    "//SYSPRINT DD    SYSOUT=*\n";
            return js.submitJCL(session, jcl, 116);
        }
        return "";
    }

    /**
     * Define storage group of pool type
     * Sample JCL: SYS1.SACBCNTL(ACBJBAJ2)
     */
    public String alterPoolStorageGroup(HttpSession session, PoolStorageGroup poolStorageGroup) {
        if (prepareTable2(session)) {
            String uid = session.getAttribute("ZOSMF_Account").toString();
            poolStorageGroup.setScds("'" + poolStorageGroup.getScds() + "'");
            String jcl = getHead(uid) +
                    "//STEP1   EXEC ACBJBAOB,\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAJ2 ALTER +\n" +
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
     * Delete volume for storage group
     * Sample JCL: SYS1.SACBCNTL(ACBJBAIB)
     */
    public String deleteVolume(HttpSession session, StorageGroupVolume volume) {
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
                    "//TEMPFILE  DD  DSN=&&VOLDELS,DISP=(NEW,KEEP),\n" +
                    "//  SPACE=(TRK,(1,1)),LRECL=300,RECFM=F,BLKSIZE=300\n" +
                    "//SYSTSIN  DD *\n" +
                    "PROFILE NOPREFIX\n" +
                    "ISPSTART CMD(ACBQBAI9) +\n" +
                    "BATSCRW(132) BATSCRD(27) BREDIMAX(3) BDISPMAX(99999999)\n" +
                    "/*\n" +
                    "//VOLDEL  DD  *\n" +
                    fieldsResolver(volume) +
                    "\n" +
                    "/*\n" +
                    "//ADDVOL2 EXEC ACBJBAOB,\n" +
                    "//        PLIB1='SYS1.DGTPLIB',\n" +
                    "//        TABL2=" + uid + ".TEST.ISPTABL\n" +
                    "//SYSUDUMP DD  SYSOUT=*\n" +
                    "//SYSTSIN  DD DSN=&&VOLDELS,DISP=(OLD,DELETE)\n";
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
