package com.zosmf.domain;

public class DatasetInfo {

    private String dsname;
    private String volser;
    private String unit;
    private String dsorg;
    private String alcunit;
    private int primary;
    private int secondary;
    private int dirblk;
    private int avgblk;
    private String recfm;
    private int blksize;
    private int lrecl;

    public String getDsname() {
        return dsname;
    }

    public void setDsname(String dsname) {
        this.dsname = dsname;
    }

    public String getVolser() {
        return volser;
    }

    public void setVolser(String volser) {
        this.volser = volser;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDsorg() {
        return dsorg;
    }

    public void setDsorg(String dsorg) {
        this.dsorg = dsorg;
    }

    public String getAlcunit() {
        return alcunit;
    }

    public void setAlcunit(String alcunit) {
        this.alcunit = alcunit;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecondary() {
        return secondary;
    }

    public void setSecondary(int secondary) {
        this.secondary = secondary;
    }

    public int getDirblk() {
        return dirblk;
    }

    public void setDirblk(int dirblk) {
        this.dirblk = dirblk;
    }

    public int getAvgblk() {
        return avgblk;
    }

    public void setAvgblk(int avgblk) {
        this.avgblk = avgblk;
    }

    public String getRecfm() {
        return recfm;
    }

    public void setRecfm(String recfm) {
        this.recfm = recfm;
    }

    public int getBlksize() {
        return blksize;
    }

    public void setBlksize(int blksize) {
        this.blksize = blksize;
    }

    public int getLrecl() {
        return lrecl;
    }

    public void setLrecl(int lrecl) {
        this.lrecl = lrecl;
    }
}
