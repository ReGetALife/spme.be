package com.spme.domain;

/**
 *  Required Fields:
 *
 *    SCDS - SCDS in which MANAGEMENT CLASS is to be DEF/ALT/DISP
 *
 *    MGMTCLAS - MANAGEMENT CLASS to be DEFINED/ALTERED/DISPLAYED
 *
 *  Optional Fields:
 *
 *    DESCR    : Type in  remarks  about the MGMTCLAS which is being
 *               defined/altered, not  exceeding  120  chars.
 *
 *    EXPNOUSE : The datasets  will  expire if they are not used for
 *               the number of days specified here.
 *
 *               Possible values  1 - 93000, NOLIMIT.  If NOLIMIT is
 *               specified the DS would not expire.
 *               Valid  only if retention  period or expiration date
 *               is not specified by the end user or is not derived
 *               from the data class.
 *
 *    EXPDTDY  : Datasets expires after  DATE/DAYS entered here.
 *               Possible values   0 - 93000, YYYY/MM/DD or NOLIMIT.
 *
 *    RETNLIM  : Possible values 0 - 93000, NOLIMIT.
 *               Use this field to control what a user or Data class
 *               can specify for retention period or expiration date
 *               during allocation. The affect of the values entered
 *               in this field are explained below.
 *
 *               0         ->  Do not  use  the RETPD and EXPDT that
 *                             the user or Dataclass specified.
 *               1 - 93000 ->  Use  this  value only if the RETPD or
 *                             EXPDT is more than this limit.
 *               NOLIMIT   ->  Do not set a limit to RETPD or EXPDT.
 *
 *    PARTREL  : Possible values  Y, C, YI, CI or N .
 *               Use this field (PARTIAL RELEASE) to specify whether
 *               allocated but unused space can be  released for DSs
 *               in this MGMTCLS. This one applies  only to VSAM DSs
 *               in extended format or NON-VSAM datasets. The values
 *               entered would have following results.
 *
 *               Y  -> Release unused space automatically during the
 *                     Space Management cycle.
 *
 *               C  -> Unused  space  can  be released automatically
 *                     only if a secondary allocation exists for the
 *                     dataset.
 *
 *               YI -> Release unused space when a dataset is closed
 *                     or during the Space Management cycle,whichever
 *                     comes first.
 *
 *               CI -> Unused  space  for  data sets  with secondary
 *                     allocation is released either when a data set
 *                     is  closed  or  during  the  Space Management
 *                     cycle, whichever comes first.
 *
 *               N  -> Do not release unused space.
 *
 *    PRINOUSE : Use this  field to specify when to migrate the DSs
 *               in this class. The possible values are
 *
 *               0         ->  To Migrate data sets  as  soon as the
 *                             space management function of DFSMShsm
 *                             is run and data integrity age is met.
 *
 *               1 to 9999 ->  Migrate  data  sets  out  of  primary
 *                             storage if  they have been unused for
 *                             this number of days or longer.
 *
 *               BLANK     ->
 *
 *    LVINOUSE : Use this filed to  specify whether DSs  can migrate
 *               to  LEVEL 1 storage  and  how  long they can remain
 *               there. The possible values are,
 *
 *               0         -> No  migration to Level 1. DSs  migrate
 *                            directly from primary storage to LVL 2
 *
 *               1 to 9999 -> The  total  number of consecutive days
 *                            that datasets  must  remain unaccessed
 *                            before  becoming  eligible  to migrate
 *                            from LVL 1 to LVL 2.
 *
 *               NOLIMIT   -> Datasets  can  not  migrate to LEVEL 2
 *                            automatically, and remain in LVL 1 for
 *                            an unlimited period.
 *
 *               BLANK     ->
 *
 *    CMDORAUT : If  migration is allowed, this field determines how
 *               the  migration is initiated. Possible values are,
 *
 *               BOTH     -> DSs  can migrate either automatically
 *                           or by command.
 *               COMMAND  -> Data sets can migrate by command only.
 *
 *               NONE     -> Data sets cannot migrate.
 *
 *    PRIGDGEL : Valid  for  Generation  Data Group (GDG) DSs  only.
 *               This  field  specifies  how  many  of  the  newest
 *               generations  of a GDG are to have  normal priority.
 *               Possible  values  are  0 - 255 or blank.For Example
 *               enter    100  if  you  want GDG generations  older
 *               than the  most recent 100, to  migrate before  non
 *               generation datasets.
 *
 *    GDGROLL  : This field  specifies  whether the  Generation  DSs
 *               in  this  MGMTCLS will expire or migrate after they
 *               have  been  removed  from  the  GDG. The  possible
 *               values are, MIGRATE,EXPIRE or blank.
 *
 *    BACKUPFR : This  field  specifies the  backup frequency.   The
 *               possible values are,
 *
 *               0        -> Backup each dataset only when the volume
 *                           it resides on is backed up.
 *               1 - 9999 -> If dataset is  changed  in the interval
 *                           between  backups, extend  the  interval
 *                           for atleast this many number of days.
 *               BLANK    ->
 *
 *    NUMBKDSE : Maximum  number  of  Backups  that   can   be  kept
 *               concurrently. Possible values are, 1 - 100,BLANK.
 *
 *    NUMBKDSD : Specifies  the  maximum no of Backups to keep after
 *               the dataset is deleted. Possible values are
 *
 *               0       -> All backups that were created are erased
 *                          after the dataset is deleted.
 *               1 - 100 -> The maximum no. of backups to keep after
 *                          a dataset has been deleted.
 *               BLANK    ->
 *
 *    RETDYDSD : Specifies how long a most recent  backup version of
 *               a deleted dataset will be kept. Possible values are
 *
 *               1 - 9999 -> After a dataset is deleted keep its most
 *                           recent  backup  version  for these many
 *                           days.
 *
 *               NOLIMIT  -> The  backup  version  will  be kept for
 *                           unlimited period.
 *
 *               BLANK    ->
 *
 *    RETDYEXT : Specifies the retention period  for  a dataset that
 *               pre-date  the  most recent  backup. Possible values
 *               are,
 *
 *               1 - 9999 -> Each backup version of a  dataset other
 *                           than the  recent copy will be  kept for
 *                           these many days.
 *
 *               NOLIMIT  -> All  backup  versions will  be kept for
 *                           unlimited period.
 *
 *               BLANK    ->
 *
 *    CMDBKUP  : Specifies who will have authority to perform command
 *               backups. Possible values are,
 *
 *               ADMIN  -> Only Storage Administrator ,
 *
 *               BOTH   -> Both Storage Administrator and end users.
 *
 *               NONE   -> Neither end user nor Storage Administrator
 *
 *    AUTOBKUP : Specifies whether the datasets in this  MGMTCLS are
 *               eligible for automatic backup. Possible  values are
 *               Y -> Yes , N -> No
 *
 *    BKUPTECH : Specifies  BACKUP COPY TECHNIQUE to be used.
 *               Possible values are,
 *
 *               R  -> Concurrent copy technique must be used.
 *               P  -> Concurrent copy technique should be used.
 *               S  -> With out the concurrent copy technique.
 *               VR -> Virtual concurrent copy technique
 *                     must be used.
 *               VP -> Virtual concurrent copy technique
 *                     should be used.
 *               CR -> Cache-based concurrent copy technique
 *                     must be used.
 *               CP -> Cache-based concurrent copy technique
 *                     should be used.
 *
 *    TMSCYRS  : No of years  that must pass since the creation date
 *               before class transition occurs. Possible values are
 *               0 - 9999, or BLANK.
 *
 *    TMSCMTH  : No of months that must pass since the creation date
 *               before class transition occurs. Possible values are
 *               0 - 9999, or BLANK.
 *
 *    TMSCDYS  : No of days   that must pass since the creation date
 *               before class transition occurs. Possible values are
 *               0 - 9999, or BLANK.
 *
 *    TMSLUYRS : No of years that must pass since the last reference
 *               date before class transition occurs.Possible values
 *               are 0 - 9999, or BLANK.
 *
 *    TMSLUMTH : No of months that must pass since the last reference
 *               date before class transition occurs.Possible values
 *               are 0 - 9999, or BLANK.
 *
 *    TMSLUDYS : No of days  that must pass since the last reference
 *               date before class transition occurs.Possible values
 *               are 0 - 9999, or BLANK.
 *
 *    PMTHODAY : The day of the month that class transition occurs.
 *               Possible values, 1 - 31, FIRST, LAST or BLANK
 *
 *    PQUAODAY : The day of the each quarter the CT occurs.
 *               Possible values, 1 - 92, FIRST, LAST or BLANK
 *
 *    PQUAIMTH : Month of each quarter the CT occurs.
 *               Possible values, 1 - 3, or BLANK
 *
 *    PYRLODAY : The day of each year the CT occurs.
 *               Possible values, 1 - 366,FIRST,LAST or BLANK
 *
 *    PYRLIMTH : The month of each year the CT occurs.
 *               Possible values, 1 - 12, or BLANK
 *
 *    VERSIONS : Specify  how  many versions of an  aggregate  group
 *               associated  with  the  management  class  are to be
 *               maintained. Possible values are 1 - 9999, NOLIMT or
 *               BLANK. If BLANK is specified no aggregate group BKP
 *               is maintained.
 *
 *    RTNOVERS : Specify  how  long the only version of an aggregate
 *               group is kept. Possible values are 1 - 9999,NOLIMIT
 *               or BLANK.
 *
 *    RTOVUNIT : Specify  the  unit of measure for the length of time
 *               specified in the above field. Possible  values  are
 *               D -> Days, W -> Weeks, M -> Months, Y -> Years  and
 *               BLANK.
 *
 *    RTNEVERS : Specify the  time  periods for which backup versions
 *               of an aggregate group are to be kept.Possible values
 *               are 1 - 9999,NOLIMIT and BLANK.
 *
 *    RTEVUNIT : Specify the unit  of measure for the length of time
 *               specified in the above field. Possible  values  are
 *               D -> Days, W -> Weeks, M -> Months, Y -> Years  and
 *               BLANK.
 *
 *    CPYSERLN : Specifies  whether you want  processing of a backup
 *               copy of an aggregate group to continue if a  shared
 *               enqueue cannot be obtained for the  datasets  being
 *               backed up. Possible values are,
 *               C -> Continue, F -> Fail or BLANK.
 *
 *    ACPYTECH : Specifies ABACKUP COPY TECHNIQUE to be used.
 *               Possible values are,
 *
 *               R  -> Concurrent copy technique must be used.
 *               P  -> Concurrent copy technique should be used.
 *               S  -> With out the concurrent copy technique.
 *               VR -> Virtual concurrent copy technique
 *                     must be used.
 *               VP -> Virtual concurrent copy technique
 *                     should be used.
 *               CR -> Cache-based concurrent copy technique
 *                     must be used.
 *               CP -> Cache-based concurrent copy technique
 *                     should be used.
 *
 *   TRCPYTECH : Specifies which copy technique should be used
 *               for the class transition of data associated with
 *               this management class. Possible values are
 *               FRP -> FR PREFERRED.
 *               FRR -> FR REQUIRED.
 *               STD -> STANDARD.
 *               PMP -> FC PRESMIRPREF.
 *               PMR -> FC PRESMIRREQ.
 *
 *  SERIALERREX: Specifies the database/exit to invoke when
 *               there is a serialization error. Possible values are
 *               DB2, CICS, ZFS, EXIT -> invokes an user exit, or
 *               NONE.
 *
 *    UPDHLVLSCDS: When modifying an SCDS, that was formatted with a
 *                 higher level of SMS, using a lower level of SMS
 *                 will make this application fail unless you
 *                 specify the UPDHLVLSCDS parameter as 'Y'.
 *                 Default is 'N'.
 *
 *                 Possible values : Y/N/BLANK
 */

@SuppressWarnings("unused")
public class ManagementClass {
    private String scds;
    private String mgmtclas;
    private String descr;
    private String expnouse;
    private String expdtdy;
    private String retnlim;
    private String partrel;
    private String prinouse;
    private String lvinouse;
    private String cmdoraut;
    private String prigdgel;
    private String gdgroll;
    private String backupfr;
    private String numbkdse;
    private String numbkdsd;
    private String retdydsd;
    private String retdyext;
    private String cmdbkup;
    private String autobkup;
    private String bkuptech;
    private String tmscyrs;
    private String tmscmth;
    private String tmscdys;
    private String tmsluyrs;
    private String tmslumth;
    private String tmsludys;
    private String pmthoday;
    private String pquaoday;
    private String pquaimth;
    private String pyrloday;
    private String pyrlimth;
    private String versions;
    private String rtnovers;
    private String rtovunit;
    private String rtnevers;
    private String rtevunit;
    private String cpyserln;
    private String acpytech;
    private String trcpytech;
    private String serialerrex;
    private String updhlvlscds;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getMgmtclas() {
        return mgmtclas;
    }

    public void setMgmtclas(String mgmtclas) {
        this.mgmtclas = mgmtclas;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getExpnouse() {
        return expnouse;
    }

    public void setExpnouse(String expnouse) {
        this.expnouse = expnouse;
    }

    public String getExpdtdy() {
        return expdtdy;
    }

    public void setExpdtdy(String expdtdy) {
        this.expdtdy = expdtdy;
    }

    public String getRetnlim() {
        return retnlim;
    }

    public void setRetnlim(String retnlim) {
        this.retnlim = retnlim;
    }

    public String getPartrel() {
        return partrel;
    }

    public void setPartrel(String partrel) {
        this.partrel = partrel;
    }

    public String getPrinouse() {
        return prinouse;
    }

    public void setPrinouse(String prinouse) {
        this.prinouse = prinouse;
    }

    public String getLvinouse() {
        return lvinouse;
    }

    public void setLvinouse(String lvinouse) {
        this.lvinouse = lvinouse;
    }

    public String getCmdoraut() {
        return cmdoraut;
    }

    public void setCmdoraut(String cmdoraut) {
        this.cmdoraut = cmdoraut;
    }

    public String getPrigdgel() {
        return prigdgel;
    }

    public void setPrigdgel(String prigdgel) {
        this.prigdgel = prigdgel;
    }

    public String getGdgroll() {
        return gdgroll;
    }

    public void setGdgroll(String gdgroll) {
        this.gdgroll = gdgroll;
    }

    public String getBackupfr() {
        return backupfr;
    }

    public void setBackupfr(String backupfr) {
        this.backupfr = backupfr;
    }

    public String getNumbkdse() {
        return numbkdse;
    }

    public void setNumbkdse(String numbkdse) {
        this.numbkdse = numbkdse;
    }

    public String getNumbkdsd() {
        return numbkdsd;
    }

    public void setNumbkdsd(String numbkdsd) {
        this.numbkdsd = numbkdsd;
    }

    public String getRetdydsd() {
        return retdydsd;
    }

    public void setRetdydsd(String retdydsd) {
        this.retdydsd = retdydsd;
    }

    public String getRetdyext() {
        return retdyext;
    }

    public void setRetdyext(String retdyext) {
        this.retdyext = retdyext;
    }

    public String getCmdbkup() {
        return cmdbkup;
    }

    public void setCmdbkup(String cmdbkup) {
        this.cmdbkup = cmdbkup;
    }

    public String getAutobkup() {
        return autobkup;
    }

    public void setAutobkup(String autobkup) {
        this.autobkup = autobkup;
    }

    public String getBkuptech() {
        return bkuptech;
    }

    public void setBkuptech(String bkuptech) {
        this.bkuptech = bkuptech;
    }

    public String getTmscyrs() {
        return tmscyrs;
    }

    public void setTmscyrs(String tmscyrs) {
        this.tmscyrs = tmscyrs;
    }

    public String getTmscmth() {
        return tmscmth;
    }

    public void setTmscmth(String tmscmth) {
        this.tmscmth = tmscmth;
    }

    public String getTmscdys() {
        return tmscdys;
    }

    public void setTmscdys(String tmscdys) {
        this.tmscdys = tmscdys;
    }

    public String getTmsluyrs() {
        return tmsluyrs;
    }

    public void setTmsluyrs(String tmsluyrs) {
        this.tmsluyrs = tmsluyrs;
    }

    public String getTmslumth() {
        return tmslumth;
    }

    public void setTmslumth(String tmslumth) {
        this.tmslumth = tmslumth;
    }

    public String getTmsludys() {
        return tmsludys;
    }

    public void setTmsludys(String tmsludys) {
        this.tmsludys = tmsludys;
    }

    public String getPmthoday() {
        return pmthoday;
    }

    public void setPmthoday(String pmthoday) {
        this.pmthoday = pmthoday;
    }

    public String getPquaoday() {
        return pquaoday;
    }

    public void setPquaoday(String pquaoday) {
        this.pquaoday = pquaoday;
    }

    public String getPquaimth() {
        return pquaimth;
    }

    public void setPquaimth(String pquaimth) {
        this.pquaimth = pquaimth;
    }

    public String getPyrloday() {
        return pyrloday;
    }

    public void setPyrloday(String pyrloday) {
        this.pyrloday = pyrloday;
    }

    public String getPyrlimth() {
        return pyrlimth;
    }

    public void setPyrlimth(String pyrlimth) {
        this.pyrlimth = pyrlimth;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getRtnovers() {
        return rtnovers;
    }

    public void setRtnovers(String rtnovers) {
        this.rtnovers = rtnovers;
    }

    public String getRtovunit() {
        return rtovunit;
    }

    public void setRtovunit(String rtovunit) {
        this.rtovunit = rtovunit;
    }

    public String getRtnevers() {
        return rtnevers;
    }

    public void setRtnevers(String rtnevers) {
        this.rtnevers = rtnevers;
    }

    public String getRtevunit() {
        return rtevunit;
    }

    public void setRtevunit(String rtevunit) {
        this.rtevunit = rtevunit;
    }

    public String getCpyserln() {
        return cpyserln;
    }

    public void setCpyserln(String cpyserln) {
        this.cpyserln = cpyserln;
    }

    public String getAcpytech() {
        return acpytech;
    }

    public void setAcpytech(String acpytech) {
        this.acpytech = acpytech;
    }

    public String getTrcpytech() {
        return trcpytech;
    }

    public void setTrcpytech(String trcpytech) {
        this.trcpytech = trcpytech;
    }

    public String getSerialerrex() {
        return serialerrex;
    }

    public void setSerialerrex(String serialerrex) {
        this.serialerrex = serialerrex;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }
}
