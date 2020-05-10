package com.spme.domain;

/**
 *  Required Fields:
 *
 *   SCDS     :  Name of SCDS, length 1-44 characters
 *
 *   STORGRP  :  Name of the storage group
 *
 *   LOWTHRS  : Low   value  of allocation/migration  threshold  to
 *              optimize the use of DASD space in a pool SG.
 *              Possible values 0-99.
 *
 *  TRKLOWTHRS: Use the low value of allocation/migration
 *              threshold track-managed to specify the threshold
 *              percentage of space allocation in the track-managed
 *              space that triggers or stops migration of data sets
 *              from volumes in this storage group during interval
 *              migration.
 *              Possible values 0-99.
 *
 *   GUARBKFR : Specifies the maximum number of days that can elapse
 *              between backups. Possible values 1 - 9999,NOLIMIT.
 *
 *   PROCPRIOR: Specifies the processing priority of the storage
 *              group. It is used during DFSMShsm space management.
 *              Possible values 1-100, default value is 50.
 *
 *  Optional Fields:
 *
 *   DESCR    :  Remarks about  the  Storage  group  being defined /
 *               altered.    Maximum length up to 120 characters.
 *
 *   AUTOMIG  :  Specifies whether the datasets  on volumes in this
 *               storage group can be moved to  DASD or TAPE by the
 *               primary  space  management and interval  migration
 *               functions of DFSMShsm. Possible values are,
 *
 *               Y  -> Datasets are eligible for primary space
 *                     management migration.
 *
 *               N  -> Datasets are not eligible for automatic
 *                     migration.
 *
 *               I  -> Datasets are eligible for primary space
 *                     management and interval migration.
 *
 *               P  -> Datasets are eligible for primary space
 *                     management but not interval migration.
 *
 *   MIGSYSNM : Name of the system or system  group where automatic
 *              migration and  space  management of the volumes  in
 *              this  storage  group  would be  performed. Possible
 *              values: System or system group names 1 - 8
 *              alphanumeric characters in length.
 *
 *   AUTOBKUP : Specifies whether all  the  volumes in  the storage
 *              are eligible for  automatic backup. Possible values
 *              are Y -> Yes and N -> No
 *
 *   BKUPSYS  : Name of the system or  system group where automatic
 *              backup function will be processed.
 *              Possible values: System or system group names 1 - 8
 *              alphanumeric characters in length.
 *
 *   AUTODUMP : Specifies whether the volumes in this storage group
 *              are to be eligible for automatic  dumping. Possible
 *              values are Y -> Yes and N -> No.
 *
 *   DMPSYSNM : Name of the system or  system  group  where volumes
 *              in this storage  group  will  automatically dump to
 *              backup devices.
 *              Possible values: System or system group names
 *              1 - 8 alphanumeric characters in length.
 *
 *   OVRFLOW  : Use this field to indicate whether the Storage Group
 *              is an overflow Storage Group. Overflow storage groups
 *              are reserved storage pools to handle periods of high
 *              demand for primary space allocations.
 *              Possible values are Y -> Yes and N -> No.
 *              Default value: N.
 *
 *   EXTSGNM  : Use this field to specify the  name of another pool
 *              storage group, the group where sets from the primary
 *              storage group can be extended to. When an extend
 *              storage group name is specified a data set may be
 *              extended to that storage group when it can not be
 *              exteneded to its currently allocated storage group.
 *              Possible values: 1 - 8 alphanumeric characters.
 *
 *   CPBSGN   : Use this field to specify the name of the storage
 *              group, which contains the eligible volumes
 *              for fast replication backup versions.
 *              Possible values: 1 - 8 alphanumeric characters.
 *
 *   DUMPCLAS : Use this field to specify 1 to 5 dump classes. ISMF
 *              neither  processes  nor verifies the values of DUMP
 *              CLASS. 1 - 8 alphanumeric characters.
 *              Classes will be separated by ','.
 *
 *   HIGHTHRS : High  value  of allocation/migration  threshold  to
 *              optimize the use of DASD space in a pool SG.
 *              Possible values 1-100.
 *
 *   TRKHITHRS: Use the high value of allocation/migration
 *              threshold track-managed to specify the threshold
 *              percentage of space allocation in the track-managed
 *              space that triggers or stops migration of data sets
 *              from volumes in this storage group during interval
 *              migration.
 *              Possible values 1-100.
 *
 *   BREAKPT  : Use this field to specify the disk space
 *              request, expressed  in number of cylinders,
 *              where the system should prefer the cylinder-managed
 *              space on an EAV.
 *              Possible values 0-65520 or blank.
 *
 *   SGSTATUS : specify this field to designate the relationship or
 *              status between storage groups and the systems in a
 *              a complex. Possible values are,
 *
 *               ENABLE  ->  System or System  Group  can allocate
 *                           and access datasets in SG. Default.
 *               DISALL  ->  System or System Group can't allocate
 *                           or  access datasets in SG.
 *               DISNEW  ->  System or System Group can't allocate
 *                           new datasets in the SG.
 *               NOTCON  ->  System or System Group is  physically
 *                           disconnected  form the SG and can not
 *                           allocate datasets in it.
 *               QUIALL  ->  If JES3, system can not schedule jobs
 *                           that allocate or access   datasets in
 *                           the SG.  In JES2 system uses  volumes
 *                           in the SG for new allocations only if
 *                           other volumes are not available.
 *               QUINEW  ->  If JES3, system can not schedule jobs
 *                           that allocate new datasets in the SG.
 *                           In case of JES2, system uses  volumes
 *                           in the SG for new allocations only if
 *                           other volumes are not available.
 *
 *               Up to 32 statuses can be specified separated by
 *               commas to match the 32 systems.  If a status is
 *               skipped, the system status that falls in between two
 *               commas will have default value of ENABLE.
 *
 *   SGSTSALL :  This field is similar to SGSTATUS in functionality.
 *               The difference is that this field accepts a single
 *               status value and sets the status of the POOL
 *               storage group on all the systems and system groups
 *               to this value.
 *
 *               Possible Values :
 *                  ENABLE/NOTCON/DISALL/DISNEW/QUIALL/QUINEW
 *
 *            Note:  SGSTSALL and SGSTATUS are mutually exclusive.
 *                   And so, while specifying value for one of these
 *                   parameters, either the other parameter should
 *                   not be specified or if specified, it should not
 *                   have any value specified.
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
public class PoolStorageGroup {
    private String scds;
    private String storgrp;
    private String descr;
    private String automig;
    private String migsysnm;
    private String autobkup;
    private String bkupsys;
    private String autodump;
    private String dmpsysnm;
    private String ovrflow;
    private String extsgnm;
    private String cpbsgn;
    private String dumpclas;
    private String highthrs;
    private String lowthrs;
    private String guarbkfr;
    private String sgstatus;
    private String sgstsall;
    private String breakpt;
    private String trkhithrs;
    private String trklowthrs;
    private String procprior;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getStorgrp() {
        return storgrp;
    }

    public void setStorgrp(String storgrp) {
        this.storgrp = storgrp;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getAutomig() {
        return automig;
    }

    public void setAutomig(String automig) {
        this.automig = automig;
    }

    public String getMigsysnm() {
        return migsysnm;
    }

    public void setMigsysnm(String migsysnm) {
        this.migsysnm = migsysnm;
    }

    public String getAutobkup() {
        return autobkup;
    }

    public void setAutobkup(String autobkup) {
        this.autobkup = autobkup;
    }

    public String getBkupsys() {
        return bkupsys;
    }

    public void setBkupsys(String bkupsys) {
        this.bkupsys = bkupsys;
    }

    public String getAutodump() {
        return autodump;
    }

    public void setAutodump(String autodump) {
        this.autodump = autodump;
    }

    public String getDmpsysnm() {
        return dmpsysnm;
    }

    public void setDmpsysnm(String dmpsysnm) {
        this.dmpsysnm = dmpsysnm;
    }

    public String getOvrflow() {
        return ovrflow;
    }

    public void setOvrflow(String ovrflow) {
        this.ovrflow = ovrflow;
    }

    public String getExtsgnm() {
        return extsgnm;
    }

    public void setExtsgnm(String extsgnm) {
        this.extsgnm = extsgnm;
    }

    public String getCpbsgn() {
        return cpbsgn;
    }

    public void setCpbsgn(String cpbsgn) {
        this.cpbsgn = cpbsgn;
    }

    public String getDumpclas() {
        return dumpclas;
    }

    public void setDumpclas(String dumpclas) {
        this.dumpclas = dumpclas;
    }

    public String getHighthrs() {
        return highthrs;
    }

    public void setHighthrs(String highthrs) {
        this.highthrs = highthrs;
    }

    public String getLowthrs() {
        return lowthrs;
    }

    public void setLowthrs(String lowthrs) {
        this.lowthrs = lowthrs;
    }

    public String getGuarbkfr() {
        return guarbkfr;
    }

    public void setGuarbkfr(String guarbkfr) {
        this.guarbkfr = guarbkfr;
    }

    public String getSgstatus() {
        return sgstatus;
    }

    public void setSgstatus(String sgstatus) {
        this.sgstatus = sgstatus;
    }

    public String getSgstsall() {
        return sgstsall;
    }

    public void setSgstsall(String sgstsall) {
        this.sgstsall = sgstsall;
    }

    public String getBreakpt() {
        return breakpt;
    }

    public void setBreakpt(String breakpt) {
        this.breakpt = breakpt;
    }

    public String getTrkhithrs() {
        return trkhithrs;
    }

    public void setTrkhithrs(String trkhithrs) {
        this.trkhithrs = trkhithrs;
    }

    public String getTrklowthrs() {
        return trklowthrs;
    }

    public void setTrklowthrs(String trklowthrs) {
        this.trklowthrs = trklowthrs;
    }

    public String getProcprior() {
        return procprior;
    }

    public void setProcprior(String procprior) {
        this.procprior = procprior;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }

    private String updhlvlscds;
}
