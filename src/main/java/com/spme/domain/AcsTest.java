package com.spme.domain;

/**
 * TEST STEP
 * <p>
 * SCDS     - NAME OF SCDS (INPUT)
 * TESTBED  - PDS CONTAINING TEST CASES (INPUT)
 * MEMBER   - MEMBERS TO BE TESTED IN TESTBED (INPUT)
 * DC,SC,MC,SG - ROUTINES TO BE TESTED (INPUT)
 * LISTNAME - TEST LISTING (OUTPUT)
 */

@SuppressWarnings("unused")
public class AcsTest {
    private String scds;
    private String testbed;
    private String member;
    private String dc;
    private String sc;
    private String mc;
    private String sg;
    private String listname;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getTestbed() {
        return testbed;
    }

    public void setTestbed(String testbed) {
        this.testbed = testbed;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }
}
