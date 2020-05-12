package com.spme.domain;

/**
 * VALIDATE STEP:
 * <p>
 * SCDS        - NAME OF SCDS THAT CONTAINS THE TRANSLATED ACS
 * ROUTINES TO BE VALIDATED (INPUT)
 * TYPE        - TYPE OF ACS ROUTINE TO BE VALIDATED (INPUT)
 * LISTNAME    - VALIDATE LISTING (OUTPUT)
 * UPDHLVLSCDS - CONFIRM OPERATION ON AN UPLEVEL SCDS(Y/N)
 */

@SuppressWarnings("unused")
public class AcsValidate {
    private String scds;
    private String type;
    private String listname;
    private String updhlvlscds;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
