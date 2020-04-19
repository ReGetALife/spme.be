package com.spme.domain;

public class DatasetInfo {

    private String dsname;
    private String volser;
    private String unit;
    private String dsorg;
    private String alcunit;
    private Integer primary;
    private Integer secondary;
    private Integer dirblk;
    private Integer avgblk;
    private String recfm;
    private Integer blksize;
    private Integer lrecl;
    private String storeclass;
    private String mgntclass;
    private String dataclass;

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

    public Integer getPrimary() {
        return primary;
    }

    public void setPrimary(Integer primary) {
        this.primary = primary;
    }

    public Integer getSecondary() {
        return secondary;
    }

    public void setSecondary(Integer secondary) {
        this.secondary = secondary;
    }

    public Integer getDirblk() {
        return dirblk;
    }

    public void setDirblk(Integer dirblk) {
        this.dirblk = dirblk;
    }

    public Integer getAvgblk() {
        return avgblk;
    }

    public void setAvgblk(Integer avgblk) {
        this.avgblk = avgblk;
    }

    public String getRecfm() {
        return recfm;
    }

    public void setRecfm(String recfm) {
        this.recfm = recfm;
    }

    public Integer getBlksize() {
        return blksize;
    }

    public void setBlksize(Integer blksize) {
        this.blksize = blksize;
    }

    public Integer getLrecl() {
        return lrecl;
    }

    public void setLrecl(Integer lrecl) {
        this.lrecl = lrecl;
    }

    public String getStoreclass() {
        return storeclass;
    }

    public void setStoreclass(String storeclass) {
        this.storeclass = storeclass;
    }

    public String getMgntclass() {
        return mgntclass;
    }

    public void setMgntclass(String mgntclass) {
        this.mgntclass = mgntclass;
    }

    public String getDataclass() {
        return dataclass;
    }

    public void setDataclass(String dataclass) {
        this.dataclass = dataclass;
    }
}
