package com.spme.domain;

/**
 * TRANSLATE STEP:
 *
 *  ACSSRC      - PDS CONTAINING ACS ROUTINES TO BE
 *                TRANSLATED (INPUT)
 *  MEMBER      - MEMBER NAME OF THE ROUTINE TO BE
 *                TRANSLATED (INPUT)
 *  SCDS        - NAME OF SCDS INTO WHICH THE ACS ROUTINES ARE
 *                TO BE TRANSLATED (OUTPUT)
 *  LISTNAME    - TRANSLATE LISTING (OUTPUT)
 *  UPDHLVLSCDS - CONFIRM OPERATION ON AN UPLEVEL SCDS
 *                When modifying an SCDS, that was formatted with a
 *                higher level of SMS, using a lower level of SMS
 *                will make this application fail unless you
 *                specify the UPDHLVLSCDS parameter as 'Y'.
 *                Default is 'N'.
 *                Possible values : Y/N/BLANK
 */

@SuppressWarnings("unused")
public class AcsTranslate {
    private String acssrc;
    private String member;
    private String scds;
    private String listname;
    private String updhlvlscds;

    public String getAcssrc() {
        return acssrc;
    }

    public void setAcssrc(String acssrc) {
        this.acssrc = acssrc;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }
}
