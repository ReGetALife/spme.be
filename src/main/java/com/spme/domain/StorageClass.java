package com.spme.domain;

/**
 *  Required Fields:
 *
 *    SCDS     : Name of the SCDS, 1-44 characters.
 *
 *    STCNAME  : Storage class being defined/altered.
 *
 *               1-8  alphanumeric  characters , begining with
 *               alphabet.
 *
 *  Optional Fields:
 *
 *    DESCR    : Remarks about the SC being created. 1-120
 *               alphanumeric characters.
 *
 *    DMSRESP  : Use DIRECT MILLISECOND RESPONSE field to  specify
 *               how quickly the system will read or write data in
 *               4K  blocks  on  direct access devices. Enter  the
 *               response time in milliseconds. All input and output
 *               requests are processed  in single 4k bytes.
 *
 *               Possible Values : 1 - 999, BLANK
 *
 *    DRTBIAS  : Specify  this  field ( DIRECT BIAS)  to  indicate
 *               whether the majority of the I/O scheduled for DSs
 *               in this SC will be READ,WRITE or UNKNOWN.
 *
 *               Possible Values : R, W, BLANK
 *
 *    SEQMSRES : Use  SEQUENTIAL  MILLISECOND  RESPONSE  field   to
 *               specify  how  quickly  the  system  will  read  or
 *               write data in  4K  blocks on sequentially accessed
 *               devices. Enter  the response time in milliseconds.
 *               All I/O requests are processed in single 4K blocks.
 *
 *               Possible Values : 1 - 999, BLANK
 *
 *    SEQBIAS  : Specify   this   field ( SEQ BIAS)  to   indicate
 *               whether the majority of the I/O scheduled for DSs
 *               in this SC will be READ,WRITE or UNKNOWN.
 *
 *               Possible Values : R, W, BLANK
 *
 *    INIARESS : Use this field (INITIAL ACCESS RESPONSE SECONDS)
 *               to  specify the  desired  response time in SECs
 *               to  locate,  mount, and prepare  media for
 *               data transfer.
 *
 *               Possible Values : 0 - 9999, BLANK
 *
 *    SUSDTRT  : Use this field (SUSTAINED  DATA RATE (MB/SEC)) to
 *               specify the sequential  data  transfer  rate  you
 *               want for striped  datasets in this SC. The system
 *               uses this value to determine the no.of stripes it
 *               will attempt to allocate for the datasets. If you
 *               enter ZERO or BLANK  the system  will  attempt to
 *               allocate  them  with  one stripe.  Only  extended
 *               sequential datasets can be striped.
 *
 *               Possible Values : 0 - 999, BLANK
 *
 *    OAMLVL   : Use the OAM SUBLEVEL field to specify the desired
 *               sublevel of an OAM disk or tape hierarchy level to
 *               use for object storage.  The OAM SUBLEVEL is only
 *               applicable when the INITIAL ACCESS RESPONSE SECONDS
 *               value is equal to 0, or when the INITIAL ACCESS
 *               RESPONSE SECONDS value is greater than 0 and the
 *               SUSTAINED DATA RATE value is greater than or equal
 *               to 3.
 *
 *               Possible values:
 *                 For disk, when IARS = 0 then OSL value of
 *                   - 1 equates to OAM Disk Sublevel 1,
 *                   - 2 equates to OAM Disk Sublevel 2,
 *                   - blank defaults to OAM Disk Sublevel 1.
 *
 *                 For tape, when IARS >= 1 and SDR >= 3 then
 *                 OSL value of
 *                   - 1 equates to OAM Tape Sublevel 1,
 *                   - 2 equates to OAM Tape Sublevel 2,
 *                   - blank defaults to OAM Tape Sublevel 1.
 *
 *                 When IARS < 1 or when IARS > 0 and SDR < 3
 *                 then OSL must be blank.
 *
 *    AVALBTY  : Specify whether dataset processing should continue
 *               after device failures.
 *
 *               Possible Values :
 *
 *               C  -> Continue to process a DS if a device failure
 *                     severs communications with the  volumes that
 *                     contains the dataset.
 *               S  -> Continuous processing  is  unavailable after
 *                     device failures.
 *               P  -> Data  may  be placed on devices that support
 *                     continuous processing.
 *               N  -> Data  is  placed  on any volume. There is no
 *                     preference among volumes.
 *
 *    ACCSBTY  : ACCESSIBILITY field specifies whether the datasets
 *               in  this  SC be  allocated to volumes supported by
 *               concurrent  copy. When  used   with  the   MGMTCLS
 *               ABACKUP/BACKUP  COPY  TECHNIQUE  attributes,  this
 *               field determines if DS should retain  write access
 *               during backup.
 *
 *               Possible Values :
 *
 *               C  -> Datasets  must  be allocated  to the volumes
 *                     supported by concurrent copy.
 *               S  -> Datasets  should be allocated to the volumes
 *                     supported by concurrent copy.
 *               P  -> Datasets should be  allocated to the volumes
 *                     not supported by concurrent copy.
 *               N  -> Datasets should be  allocated to the volumes
 *                     whether  the volumes support concurrent copy
 *                     or not.
 *
 *    BACKUP   : Use  the  BACKUP  option  under ACCESSIBILITY  to
 *               specify  that  the datasets  in  this storage class
 *               when being backed up are unavailable to application
 *               programs for a minimal period.
 *
 *               Possible Values :
 *
 *               Y      -> Dataset is available for backup.
 *               N      -> Dataset is not available for backup.
 *               Blank  -> Defaults to N.
 *
 *    VRSNING  : Use the VERSIONING option  under ACCESSIBILITY  to
 *               specify  that  the datasets  in  this storage class
 *               can  have  a  point-in-time  version  available for
 *               application  testing,  reporting,  or  backup while
 *               applications  continue  to  access  and  update the
 *               source dataset.
 *
 *               Possible Values :
 *
 *               Y      -> Dataset is available for Versioning.
 *               N      -> Dataset is not available for Versioning.
 *               Blank  -> Defaults to N.
 *
 *    GURNTSPC : GUARANTEED  SPACE  field  specifies whether to use
 *               the JCL VOL=SER=  parameter  to  reserve  space on
 *               specific volumes for datasets in this SC.
 *
 *               Possible Values :
 *
 *               Y  -> Reserve  space  for datasets on the  volumes
 *                     specified in the JCL VOL=SER= parameter.
 *               N  -> Don't  use  volumes  requested  in  the  JCL
 *                      VOL=SER= parameter.
 *
 *    GUASYNWR : GUARANTEED  SYNCHRONOUS  WRITE   field   indicates
 *               whether  the  system  should  return  from  a BSAM
 *               CHECK  (or WAIT)  issued  for  a  WRITE  against a
 *               partitioned  dataset  extended (PDSE)  member or a
 *               compressed format data set before (unsynchronized)
 *               or after (synchronized) the data has been written
 *               to a storage device.
 *
 *               Possible Values :
 *
 *               Y  -> Indicates synchronized write.
 *               N  -> Indicates no synchronization.
 *
 *
 *    MULTITSG : MULTI TIER SGs field indicates whether or not SMS
 *               attempt to allocate using a volume in the first
 *               listed storage group prior to allocating against
 *               subsequent storage groups.
 *
 *               Possible Values : Y/N/BLANK
 *
 *
 *    PAVCAP   : Use the PARALLEL ACCESS VOLUME CAPABILITY field
 *               to modify the volume preferencing.
 *
 *               Possible Values : R/P/S/N
 *
 *    CFCACSTN : Coupling Facility Cache set name.
 *
 *               Possible Values :
 *
 *               1-8 alphanumeric characters with beginning char
 *
 *    CFDTWGHT : Specifies relative importance of  data in a SC
 *               when it is accessed directly.
 *
 *               Possible Values : 1 - 11, BLANK
 *
 *    CFSEQWHT : Specifies relative importance of  data in a SC
 *               when it is accessed sequentially.
 *
 *               Possible Values : 1 - 11, BLANK
 *
 *    CFLOCSTN : Coupling Facility Lock set name.
 *
 *               Possible Values :
 *
 *               1-8 alphanumeric characters with beginning char
 *
 *    DISCSPHERE: Disconnect Sphere at CLOSE field indicates
 *                whether the sphere should be disconnected
 *                immediately upon closing the data set or stay
 *                connected for a period of time.
 *
 *                Possible Values :
 *
 *                Y      -> Yes, the sphere will be immediately
 *                          disconnected as soon as the data set is
 *                          closed.
 *                N      -> No, sphere stays connected for a period
 *                          of time following the "CLOSE".
 *
 *    UPDHLVLSCDS: When modifying an SCDS, that was formatted with
 *                 a higher level of SMS, using a lower level of
 *                 SMS will make this application fail unless you
 *                 specify the UPDHLVLSCDS parameter as 'Y'.
 *                 Default is 'N'.
 *
 *                 Possible values : Y/N/BLANK
 */

@SuppressWarnings("unused")
public class StorageClass {
    private String scds;
    private String stcname;
    private String descr;
    private String dmsresp;
    private String drtbias;
    private String seqmsres;
    private String seqbias;
    private String iniaress;
    private String susdtrt;
    private String oamlvl;
    private String avalbty;
    private String accsbty;
    private String backup;
    private String vrsning;
    private String gurntspc;
    private String guasynwr;
    private String multitsg;
    private String pavcap;
    private String cfcacstn;
    private String cfdtwght;
    private String cfseqwht;
    private String cflocstn;
    private String discsphere;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getStcname() {
        return stcname;
    }

    public void setStcname(String stcname) {
        this.stcname = stcname;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDmsresp() {
        return dmsresp;
    }

    public void setDmsresp(String dmsresp) {
        this.dmsresp = dmsresp;
    }

    public String getDrtbias() {
        return drtbias;
    }

    public void setDrtbias(String drtbias) {
        this.drtbias = drtbias;
    }

    public String getSeqmsres() {
        return seqmsres;
    }

    public void setSeqmsres(String seqmsres) {
        this.seqmsres = seqmsres;
    }

    public String getSeqbias() {
        return seqbias;
    }

    public void setSeqbias(String seqbias) {
        this.seqbias = seqbias;
    }

    public String getIniaress() {
        return iniaress;
    }

    public void setIniaress(String iniaress) {
        this.iniaress = iniaress;
    }

    public String getSusdtrt() {
        return susdtrt;
    }

    public void setSusdtrt(String susdtrt) {
        this.susdtrt = susdtrt;
    }

    public String getOamlvl() {
        return oamlvl;
    }

    public void setOamlvl(String oamlvl) {
        this.oamlvl = oamlvl;
    }

    public String getAvalbty() {
        return avalbty;
    }

    public void setAvalbty(String avalbty) {
        this.avalbty = avalbty;
    }

    public String getAccsbty() {
        return accsbty;
    }

    public void setAccsbty(String accsbty) {
        this.accsbty = accsbty;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getVrsning() {
        return vrsning;
    }

    public void setVrsning(String vrsning) {
        this.vrsning = vrsning;
    }

    public String getGurntspc() {
        return gurntspc;
    }

    public void setGurntspc(String gurntspc) {
        this.gurntspc = gurntspc;
    }

    public String getGuasynwr() {
        return guasynwr;
    }

    public void setGuasynwr(String guasynwr) {
        this.guasynwr = guasynwr;
    }

    public String getMultitsg() {
        return multitsg;
    }

    public void setMultitsg(String multitsg) {
        this.multitsg = multitsg;
    }

    public String getPavcap() {
        return pavcap;
    }

    public void setPavcap(String pavcap) {
        this.pavcap = pavcap;
    }

    public String getCfcacstn() {
        return cfcacstn;
    }

    public void setCfcacstn(String cfcacstn) {
        this.cfcacstn = cfcacstn;
    }

    public String getCfdtwght() {
        return cfdtwght;
    }

    public void setCfdtwght(String cfdtwght) {
        this.cfdtwght = cfdtwght;
    }

    public String getCfseqwht() {
        return cfseqwht;
    }

    public void setCfseqwht(String cfseqwht) {
        this.cfseqwht = cfseqwht;
    }

    public String getCflocstn() {
        return cflocstn;
    }

    public void setCflocstn(String cflocstn) {
        this.cflocstn = cflocstn;
    }

    public String getDiscsphere() {
        return discsphere;
    }

    public void setDiscsphere(String discsphere) {
        this.discsphere = discsphere;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }

    private String updhlvlscds;
}
