package com.spme.domain;

/**
 *   SCDS    : Specify the SCDS whose base configuration you want
 *             to define/alter or display.
 *
 *   BYTPTRK : Specify BYTES/TRACK
 *
 *             Possible values : 1 - 999999
 *
 *   TRKPCYL : Specify TRACKS/CYLENDER
 *
 *             Possible values : 1 - 999999
 *
 *   ADDSYS  : Name of the system you want to add
 *
 *             Possible values : 1-8 Alphanumeric characters
 *                               beginning with alphebetic.
 *
 *             If more than one system is being added  , systems
 *             will be separated by ','.
 *
 *             Ex : ADDSYS(SYS1,SYS2,SYS3)
 *
 *   ADDGRP  : Name of the System group that you want to add
 *
 *             Possible values : 1-8 Alphanumeric characters.
 *                               beginning with aplhabetic.
 *
 *             If more than one system group is being added, system
 *             groups will  be separated by ','.
 *
 *             Ex : ADDGRP(SGRP1,SGRP2,SGRP3)
 *
 *
 *   DELSYS  : Name of the system you want to delete
 *
 *             If more than one system is being deleted, systems
 *             will be separated by ','.
 *
 *             Ex : DELSYS(SYS1,SYS2,SYS3)
 *
 *   DELGRP  : Name of the System group that you want to delete
 *
 *             If more than one system group is being deleted,
 *             system groups will  be separated by ','.
 *
 *             Ex : DELGRP(SGRP1,SGRP2,SGRP3)
 *
 *   RENSYS  : Old and new names of the system that is being
 *             renamed, separated by ','.
 *
 *             If more than one system is being renamed, systems
 *             will be separated by ','.
 *
 *             Ex : RENSYS(oldname1,newname1,oldname2,newname2)
 *
 *   RENGRP  : Old and new names of the system group  that is being
 *             renamed, separated by ','.
 *
 *             If more than one system group is being renamed,
 *             system groups ll be separated by ','.
 *
 *             Ex : RENGRP(oldname1,newname1,oldname2,newname2)
 *
 * Optional Fields:
 *
 *   DESCR   : Remarks  about  the  SCDS whose base you want to
 *             define or alter.
 *
 *             Possible values : 1 - 120 characters.
 *
 *   DEFMC   : Specify this DEFAULT MGMTCLS field to define or
 *             alter the MGMTCLS reserved  for  datasets which
 *             have not yet been assigned a MGMTCLS. Available
 *             only for SMS managed datasets. Controls the BKP,
 *             retention and migration of datasets.
 *
 *             Possible values : Valid MGMTCLS name.
 *
 *   DEFUNIT : Specify this field  to assign the default device
 *             used when non SMS datasets are allocated without
 *             a unit parameter,
 *
 *   DSSEPPL : Specify Partitioned or Sequential dataset name
 *             This provides SMS with a list of dataset names.
 *
 *             Possible values : A valid data set name
 *
 *   UPDHLVLSCDS: When modifying an SCDS, that was formatted with a
 *                higher level of SMS, using a lower level of SMS
 *                will make this application fail unless you
 *                specify the UPDHLVLSCDS parameter as 'Y'.
 *                Default is 'N'.
 *
 *                Possible values : Y/N/BLANK
 */
@SuppressWarnings("unused")
public class CdsBaseConfig {
    private String scds;
    private String bytptrk;
    private String trkpcyl;
    private String addsys;
    private String addgrp;
    private String delsys;
    private String delgrp;
    private String rensys;
    private String rengrp;
    private String descr;
    private String defmc;
    private String defunit;
    private String dsseppl;
    private String updhlvlscds;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getBytptrk() {
        return bytptrk;
    }

    public void setBytptrk(String bytptrk) {
        this.bytptrk = bytptrk;
    }

    public String getTrkpcyl() {
        return trkpcyl;
    }

    public void setTrkpcyl(String trkpcyl) {
        this.trkpcyl = trkpcyl;
    }

    public String getAddsys() {
        return addsys;
    }

    public void setAddsys(String addsys) {
        this.addsys = addsys;
    }

    public String getAddgrp() {
        return addgrp;
    }

    public void setAddgrp(String addgrp) {
        this.addgrp = addgrp;
    }

    public String getDelsys() {
        return delsys;
    }

    public void setDelsys(String delsys) {
        this.delsys = delsys;
    }

    public String getDelgrp() {
        return delgrp;
    }

    public void setDelgrp(String delgrp) {
        this.delgrp = delgrp;
    }

    public String getRensys() {
        return rensys;
    }

    public void setRensys(String rensys) {
        this.rensys = rensys;
    }

    public String getRengrp() {
        return rengrp;
    }

    public void setRengrp(String rengrp) {
        this.rengrp = rengrp;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDefmc() {
        return defmc;
    }

    public void setDefmc(String defmc) {
        this.defmc = defmc;
    }

    public String getDefunit() {
        return defunit;
    }

    public void setDefunit(String defunit) {
        this.defunit = defunit;
    }

    public String getDsseppl() {
        return dsseppl;
    }

    public void setDsseppl(String dsseppl) {
        this.dsseppl = dsseppl;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }
}
