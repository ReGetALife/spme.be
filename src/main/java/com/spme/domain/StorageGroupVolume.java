package com.spme.domain;

/**
 * Required Fields:
 *
 *      SCDS     - SOURCE CONTROL DATA SET
 *
 *      VOL      - THIS FIELD CAN CONTAIN EITHER A VOLUME SERIAL
 *                 NUMBER OR THE FIRST COMMON CHARACTERS IN A RANGE
 *                 OF VOLUME SERIAL NUMBERS. IF PART OF RANGE,
 *                 ENTER THE REMAINING DISTINCTIVE CHARACTERS OF
 *                 THE VOLUME SERIAL NUMBERS IN THE 'FROM' AND 'TO'
 *                 FIELDS, AND IF YOU WANT, THE 'SUF' FIELD.
 *
 *      SG       - STORAGE GROUP
 *
 * Optional Fields:
 *
 *  Set the criteria in the following fields to specify range of
 *  volumes (you can specify as many as 100 volumes at a time):
 *
 *      FROM     - Use this field to specify the characters unique
 *                 to the first volume serial number in a range.
 *                 Use the VOL and SUF fields to select leading
 *                 and trailing characters. Valid entries are
 *                 decimal or hexadecimal numbers or alphabet.
 *
 *      TO       - Use this field to specify the characters unique
 *                 to the last volume serial number in a range.
 *                 Use the VOL and SUF fields to  select leading
 *                 and trailing characters. Valid entries are
 *                 decimal or hexadecimal numbers or alphabet.
 *
 *      SUF      - When you specify values in the FROM and TO
 *                 fields, you can specify in this field the
 *                 common trailing characters in a range of volume
 *                 serial numbers. Enter up to five characters.
 *
 *      TYPE     - When you specify hexadecimal numbers in the FROM
 *                 and TO fields, you must enter 'X' in this field.
 *                 When you specify alphabets in the FROM and TO
 *                 fields, you must enter 'A' in this field.
 *                 The default is decimal.
 *
 *      STATUS   - STATUS (ENABLE/NOTCON/DISALL/DISNEW/QUIALL/
 *                         QUINEW)
 *                Up to 32 statuses can be specified separated by
 *               commas to match the 32 systems.  If a status is
 *               skipped, the system status that falls in between 2
 *               commas will have default value of ENABLE.
 *
 *      STATUSALL - STATUSALL (ENABLE/NOTCON/DISALL/DISNEW/QUIALL/
 *                         QUINEW)
 *                If Volume status in all the Systems needs to be
 *               set to a single value (for example ENABLE),
 *               STATUSALL is an easier option compared to the
 *               parameter STATUS.
 *
 *           Note:  STATUSALL and STATUS are mutually exclusive.
 *                  And so, while specifying value for one of these
 *                  parameters, either the other parameter should
 *                  not be specified or if specified, it should not
 *                  have any value specified.
 *
 *      UPDHLVLSCDS - When modifying an SCDS, that was formatted
 *                    with a higher level of SMS, using a lower
 *                    level of SMS will make this application fail
 *                    unless you specify the UPDHLVLSCDS parameter
 *                    as 'Y'.  Default is 'N'.
 *
 *                      If specified, this should be the first
 *                    parameter on  either VOLDEL or ADDVOL DD
 *                    names.
 *
 *                    Possible values : Y/N/BLANK
 *
 */

@SuppressWarnings("unused")
public class StorageGroupVolume {
    private String scds;
    private String vol;
    private String sg;
    private String from;
    private String to;
    private String suf;
    private String type;
    private String status;
    private String statusall;
    private String updhlvlscds;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSuf() {
        return suf;
    }

    public void setSuf(String suf) {
        this.suf = suf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusall() {
        return statusall;
    }

    public void setStatusall(String statusall) {
        this.statusall = statusall;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }
}
