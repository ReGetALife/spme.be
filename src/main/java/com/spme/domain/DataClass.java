package com.spme.domain;

/**
 *  Required Fields:
 *
 *    SCDS     : Specify the name of the CDS  that  contains the
 *               dataclass you want to Define/Alter/Display.
 *
 *               Possible values : Valid CDS name .
 *
 *    DCNAME   : Name of the Dataclass.
 *
 *               Possible values : 1 - 8 characters
 *
 *  Optional Fields:
 *
 *    DESCR    : Remarks about the DC being defined/altered.
 *               1-120 characters.
 *
 *    RECORG   : Specify how the records in the Datasets will be
 *               organized during allocation.
 *
 *               Possible values :
 *
 *               KS    ->  VSAM Key Sequenced
 *               ES    ->  VSAM Entry Sequenced
 *               RR    ->  VSAM Relative Record
 *               LS    ->  VSAM Linear Space
 *               BLANK ->  PS or PDS
 *
 *    RECFM    : Specify the format of records for Non VSAM DSs
 *               in this dataclass.
 *
 *               Possible Values :
 *
 *               UA|MÙ   ->  Undefined
 *               VA|MÙ   ->  Variable
 *               VSA|MÙ  ->  Variable Spanned
 *               VBA|MÙ  ->  Variable Blocked
 *               VBSA|MÙ ->  Variable Blocked & Spanned
 *               FA|MÙ   ->  Fixed
 *               FSA|MÙ  ->  Fixed Standard
 *               FBA|MÙ  ->  Fixed Blocked
 *               FBSA|MÙ ->  Fixed Blocked Standard
 *               BLANK    ->  Specify no record format
 *
 *    LRECL    : Specify the logical record length of records in
 *               this dataclass.For variable length or undefined
 *               records this is the maximum length of a record.
 *
 *               Possible Values :
 *
 *               1 to 32760 or BLANK if RECORG is BLANK
 *               1 to 32761 or BLANK if RECORG is ES, KS, or RR
 *
 *    KEYLEN   : Specify the length of the key field for records
 *               in this dataclass.
 *
 *               Possible Values :
 *
 *               0 to 255 or blank if RECORG is blank
 *               1 to 255 or blank if RECORG is KS
 *
 *    KEYOFF   : Specify key offset  for  key sequenced datasets
 *               in this DC.
 *
 *               Possible Values :  0 - 32760, BLANK
 *
 *    SPCAVREC : Specify space units.
 *
 *               Possible Values :
 *
 *               K     -> Kilo Bytes
 *               M     -> Mega Bytes
 *               U     -> Bytes
 *               BLANK ->
 *
 *    SPCAVVAL : Average length of each record in bytes.
 *
 *               Possible Values :  0 - 65535 or blank
 *
 *    SPCPRM   : Specify number of records Primary storage will
 *               contain.
 *
 *               Possible Values :  0 - 999999 or blank
 *
 *    SPCSEC   : Specify number of records Secondary storage will
 *               contain.
 *
 *               Possible Values :  0 - 999999 or blank
 *
 *    SPCDIR   : Specify no. of directory blocks to be allocated
 *               for PDS.
 *
 *               Possible Values :  0 - 999999 or blank
 *
 *    OVERRIDE : Specify whether or not the data class space
 *               attributes will override the space attributes from
 *               other sources like JCL.
 *
 *               Possible Values :
 *
 *               Y -> Yes, override attributes
 *               N -> No, do not override attributes
 *               BLANK   -> Default N is assumed
 *
 *    REXPPDT  : Specify  the   default   retention  period  or
 *               expiration  date of datasets in this DC.
 *
 *               Possible Values :
 *
 *               0 to 93000 ->  Datasets expire in no. of days
 *               yyyy/mm/dd ->  Datasets expiry date
 *                              yyyy =>  1900 - 2155
 *               yyyy/00/00 ->  Special value
 *                              yyyy =>  1900 - 2155
 *               BLANK      ->  No expiration date specified
 *
 *    VOLCNT   : Maximum no of volumes you expect to a DS in
 *               this DC.
 *
 *               Possible Values :  1 - 59, BLANK (DASD)
 *                                  1 - 255, BLANK (Tape)
 *
 *    ADDVOLAM : Specify the allocation amount when a VSAM DS
 *               in  extended  format  begins  allocation  on
 *               subsequent volumes.
 *
 *               Possible Values :
 *
 *               P        Use primary  allocation  amount
 *               S        Use secondary allocation amount
 *               BLANK    Use  default  value  of primary
 *
 *
 *    CISZDATA : Specify the size of each Control Interval for DS
 *               in this data class. Applies  only  to  VSAM DSs
 *               with RECORG of KS,ES,LS or RR only.
 *
 *               Possible Values :  1 - 32768, BLANK
 *
 *    FRSPCCI  : Percentage of free space you  want to reserve in
 *               the CI to avoid splits. Valid only for VSAM KSDS.
 *
 *               Possible Values :  0 - 100
 *
 *    FRSPCCA  : Percentage of free space you  want to reserve in
 *               the CA to avoid splits. Valid only for VSAM KSDS.
 *
 *               Possible Values :  0 - 100
 *
 *    SHRXREG  : Specify how the data will be shared with in one
 *               system. Applies to VSAM DSs  with RECORG of
 *               KS,ES,LS and RR only.
 *
 *               Possible Values :
 *
 *               1  -> All users can  read the DS when no one is
 *                     writing to it.
 *               2  -> All  users  can  read the  DS even if one
 *                     user is writing to it.
 *               3  -> All users can both read  and write to the
 *                     DS. VSAM doesn't ensure the data integrity.
 *               4  -> All users can both read  and write to the
 *                     DS. VSAM provides some assistance to ensure
 *                     data integrity.
 *               BLANK -> No share options specified.
 *
 *    SHRXSYS  : Specify  how  the data will be shared among the
 *               systems. Applies to VSAM DSs  with RECORG
 *               KS,ES,LS and RR only.
 *
 *               Possible values : (See above)
 *
 *    COMPTN   : Specify whether tape  volumes or DASD  datasets
 *               associated with this DC  are to be compacted or
 *               compressed.
 *
 *               Possible values :
 *
 *               Y     -> Extended format data sets are
 *                        compressed and tape volumes are compacted.
 *                        The type of DASD compression depends
 *                        on the COMPRESS option in IGDSMSxx.
 *               N     -> DSs are  not compressed and Tape volumes
 *                        are  not  compacted  unless requested by
 *                        USER on JCL/DYNAMIC allocation.
 *               T     -> Extended format data sets are
 *                        compressed using tailored dictionaries
 *                        overriding SYS1.PARMLIB.
 *               G     -> Extended format data sets are compressed
 *                        using generic dictionaries overriding
 *                        SYS1.PARMLIB.
 *               ZR    -> The system will fail the allocation
 *                        request if the zEDC function is not
 *                        supported by the system or the minimum
 *                        allocation amount requirement is not met.
 *               ZP    -> The system will not fail the allocation
 *                        request but rather create either a
 *                        tailored compressed data set if the zEDC
 *                        function is not supported by the system
 *                        or create a non-compressed extended format
 *                        data set if the minimum allocation amount
 *                        requirement is not met.
 *               BLANK -> DSs are  not compressed and Tape volumes
 *                        are  not  compacted  unless requested by
 *                        USER on JCL/DYNAMIC allocation or by the
 *                        installation through parmlib specification.
 *
 *    MDTYPE   : Specify mountable tape cartridge type used for
 *               DSs  associated with this DC.
 *
 *               Possible values :
 *
 *               1     ->  For MEDIA1 ( Non Scalable
 *                                      Cartridge System Tape)
 *               2     ->  For MEDIA2 ( Enhanced Capacity
 *                                      Non Scalable
 *                                      Cartridge System Tape)
 *               3     ->  For MEDIA3 ( High Performance
 *                                      Non Scalable
 *                                      Cartridge Tape)
 *               4     ->  For MEDIA4 ( Extended
 *                                      High Performance
 *                                      Non Scalable
 *                                      Cartridge Tape)
 *               5     ->  For MEDIA5 ( Enterprise Tape
 *                                       Cartridge 3592 )
 *               6     ->  For MEDIA6 ( WORM Tape Cartridge)
 *               7     ->  For MEDIA7 ( Enterprise Economy
 *                                      Tape Cartridge)
 *               8     ->  For MEDIA8 ( Enterprise Economy WORM
 *                                      Tape Cartridge)
 *               9     ->  For MEDIA9 ( Enterprise Extended
 *                                      Tape Cartridge 3592)
 *               10    ->  For MEDIA10( Enterprise Extended
 *                                      WORM Tape Cartridge 3592)
 *               11    ->  For MEDIA11( Enterprise Advanced
 *                                      Tape Cartridge)
 *               12    ->  For MEDIA12( Enterprise Advanced WORM
 *                                      Tape Cartridge)
 *               13    ->  For MEDIA13( Enterprise Advanced
 *                                      Economy Tape Cartridge)
 *               BLANK ->  Cartridge type not specified.
 *
 *    RECTECH  : Specify the number of tracks on  mountable  tape
 *               cartridges used for DSs  associated with this DC.
 *               Optional.
 *
 *               Possible values :
 *
 *               18    -> An 18-track cartridge is used.
 *               36    -> A 36-track cartridge is used.
 *               128   -> A 128-track cartridge is used.
 *               256   -> A 256-track cartridge is used.
 *               384   -> A 384-track cartridge is used.
 *               E1    -> An Enterprise Format 1 cartridge is
 *                        used.
 *               E2    -> An Enterprise Format 2 cartridge is
 *                        used.
 *               E3    -> An Enterprise Format 3 cartridge is
 *                        used.
 *               E4    -> An Enterprise Format 4 cartridge is
 *                        used.
 *               EE2   -> An Enterprise Encrypted Format 2
 *                        cartridge is used.
 *               EE3   -> An Enterprise Encrypted Format 3
 *                        cartridge is used.
 *               EE4   -> An Enterprise Encrypted Format 4
 *                        cartridge is used.
 *               BLANK -> The system default is used.
 *
 *    PEFSCLG  : Provides the installation with the ability to
 *               select tape usage.
 *               Optional.
 *
 *               Possible values :
 *
 *               Y     -> Provides optimal performance.
 *               N     -> To use the full capacity.
 *               BLANK -> Same as 'N' option.
 *
 *    PERFSEG  : Provides the installation with the ability to
 *               select tape usage.
 *               Optional.
 *
 *               Possible values :
 *
 *               Y     -> Enable Segmentation.
 *               N     -> No Segmentation.
 *               BLANK -> Same as 'N' option.
 *
 *    DSNMTYP  : Specify the format of the datasets in this DC.
 *
 *               Possible values :
 *
 *               EXT      -> Extended format datasets
 *               HFS      -> Hierarchical File System Datasets
 *               LIB      -> Datasets are allocated as PDSEs
 *               PDS      -> Datasets are allocated as PDSs
 *               LARGE    -> Large format sequential data sets
 *                             ( > 65535 tracks)
 *               BLANK    -> Not specified
 *
 *    IFEXT    : Specify whether this DC requires the DSs to be
 *               allocated only in extended  sequential format.
 *               Required only if DSNMTYP is specified as EXT.
 *
 *               Possible values :
 *
 *               P     -> Preferred
 *               R     -> Required
 *               BLANK -> DSNMTYP is not EXT.
 *
 *    EXTADDRS : Specify this field to provide datasets with
 *               addressability of more than 4 GB.
 *
 *               Possible values :
 *
 *               Y -> Provides extended addressability if
 *                    DSNMTYP is EXT
 *               N -> Doesn't provide extended addressability.
 *
 *    RECACCB  : Use this field to specify to VSAM, how  the
 *               buffers are to be chosen and the manner by
 *               which they are processed.
 *
 *               Possible values :
 *
 *               S     -> VSAM to determine buffering  algorithm
 *               U     -> Buffers are obtained in the same manner
 *                        as is used with out SYSTEM MANAGED
 *                        BUFFERING.
 *               DO    -> SMB with direct optimization
 *               DW    -> SMB weighted for direct processing
 *               SO    -> SMB with sequential optimization
 *               SW    -> SMB weighted for sequential processing
 *               BLANK -> U assumed if   DSNMTYP => EXT
 *
 *    REUSE    : Specify whether or not the users can open the
 *               cluster again and again as  a new cluster.
 *
 *               Possible values :
 *
 *               Y -> Reusable
 *               N -> Non Reusable
 *               BLANK   -> Default N is assumed
 *
 *    INILOAD  : Specify whether or not storage allocated to the
 *               data component  is  to  be  preformatted before
 *               records are inserted during initial load.
 *
 *               Possible values :
 *
 *               S       -> No preformat
 *               R       -> Data components control areas written
 *                          with records that indicate EOF.
 *               BLANK   -> Default R is assumed
 *
 *    SPANONSP : Specify whether a data record is  allowed to
 *               cross   Control   Interval  boundaries. This
 *               attribute cannot be defined  while  defining
 *               linear dataset cluster.
 *
 *               Possible values :
 *
 *               S     ->  record can be spanned across CIs
 *               N     ->  Record must be contained in one CI
 *               BLANK ->  Default  N is assumed.
 *
 *    BWO      : Specify this field if Backup-While-Open (BWO)
 *               is allowed for  sphere. BWO  applies  only to
 *               SMS VSAM  datasets  and can not be  used with
 *               TYPE(LINEAR).
 *
 *               Possible values :
 *
 *               TC    -> Use TYPECICS for CICS VSAM file control
 *                        datasets. CICS use BWO for RLS and FVT
 *                        for Non RLS processing.
 *               TI    -> Use TYPEIMS for IMS VSAM datasets
 *               NO    -> BWO doesn't apply to the cluster
 *               BLANK -> CICS uses FCT definition for RLS and
 *                        non RLS processing.
 *
 *    LOG      : Specify whether the sphere to be accessed with
 *               VSAM record level sharing(RLS) is recoverable
 *               or non-recoverable.
 *
 *               Possible values :
 *
 *               NoneÙ -> Non recoverable
 *               UndoÙ -> recoverable
 *               AllÙ  ->
 *               Blank  ->
 *
 *    LOGSTID  : This field identifies the CICS recovery log stream.
 *
 *               Possible values :
 *
 *               Dataset name of the recovery log stream
 *               BLANK
 *
 *    FRLOG    : Used to request RCC batch logging when the
 *               cluster is accessed in NSR, LSR, or GSR mode.
 *
 *               Possible values :
 *
 *               AllÙ  -> VSAM will call RCC to log changes to the
 *                         both RCC forward and backward recovery
 *                         log stream.
 *               NoneÙ -> No logging.
 *               RedoÙ -> VSAM will call RCC to log changes to the
 *                         RCC forward recovery log stream.
 *               UndoÙ -> VSAM will call RCC to log changes
 *                         to the RCC backward recovery log stream.
 *               blank  ->
 *
 *    SPCCONRL : Specify the DC attributes  that will be used by
 *               the  system  to retry  allocation and extension
 *               failures for SMS datasets.
 *
 *               Possible values :
 *
 *               Y  -> SMS will re-drive the allocation after
 *                     reducing the  required space  quantity
 *                     based on the REQUIRED SPACE UPTO parm.
 *               N  -> Default; SMS will not attempt  a retry
 *
 *    REDSPCUT : specify this field ( REDUCE SPACE UPTO) in order
 *               to reduce the amount of requested quantity by x%
 *               and re-drive the best fit allocation.
 *
 *               Possible values : 0 - 99, BLANK.
 *                                                             15@U1A
 *    BLKSZLIM : Specify  this field  (BLOCK SIZE LIMIT), to  specify
 *               the largest value that  the system can determine for
 *               a data set  block  size  when a program opens a data
 *               set for output and no value is available. The system
 *               determines a data set block size that is appropriate
 *               for the media type  when the data set is sequential,
 *               the  record  format  is  fixed  or  variable and the
 *               logical record length is known. The BLKSZLIM keyword
 *               on the DD statement overrides this value.
 *
 *               Possible Values : 32760 to 2147483648,
 *                                 32KB  to 2097152KB,
 *                                 1MB   to 2048MB,
 *                                 1GB   to 2GB, Blank.
 *
 *    RLSCF    : Specify RLS CF CACHE VALUE field to cache
 *               VSAM RLS data with CI's greater than 4K defined to
 *               DFSMS CF cache structures.
 *
 *               Possible values :
 *
 *               AllÙ         -> Indicates that all of the data is
 *                                cached for the sphere.
 *               NoneÙ        -> Indicates that none of the data
 *                                will be cached
 *               UpdatesonlyÙ -> Indicates that only updated Cls
 *                                will be cached
 *               DironlyÙ     -> Indicates that the directory
 *                                only will be cached
 *
 *    MAXVOL   : The DYNAMIC VOLUME COUNT parameter in the
 *               SMS data class which causes DFSMS to dynamically
 *               add volumes to an SMS managed data set up to the
 *               maximum specified in the data class.
 *
 *               Possible Values :  1 - 59, BLANK
 *
 *    EXTCONS  : Specify whether or not a VSAM data set is
 *               allowed to go beyond the 255 extents limit.
 *
 *               Possible values :
 *               Y -> Remove the 255 extents limit
 *               N -> Keep the limit of 255 extents
 *               BLANK   -> Default N is assumed
 *
 *    RLSABOVE : Specify whether or not virtual storage for RLS
 *               can be above the 2-Gigabyte bar.
 *
 *               Possible values :
 *               Y -> Place buffers above the bar in the SYSVSAM
 *                    address space
 *               N -> Do not place any data in storage located
 *                    above the bar
 *               BLANK   -> Default N is assumed
 *
 *    SMBVSP   : Specify the amount of virtual storage
 *               to obtain for buffers when opening the data set.
 *
 *               Possible values  :
 *               1K  to  2048000K
 *               1M  to  2048M
 *               BLANK
 *
 *    SDB      : Specify whether or not the system determined
 *               blocksize will be used regardless of the existence
 *               of a user-specified blocksize.
 *
 *               Possible Values :
 *
 *               Y -> Yes, use SDB
 *               N -> No, do not USE SDB
 *               BLANK   -> Default N is assumed
 *
 *   KEYLABL1, : Specifies the label for the key encrypting
 *   KEYLABL2    key used by the key manager. The key encrypting key
 *               is used by the key manager to encrypt the data
 *               (encryption) key.
 *
 *               Possible Values :
 *
 *               - up to 64 characters typically containing alpha-
 *                 numeric, national or special characters with some
 *                 additional characters also being allowed; treated
 *                 as a free form field on input and validity checked
 *                 by the control unit when the key label is first
 *                 used and converted from EBCDIC -> ASCII.  Char-
 *                 acters specified must map to ASCII characters
 *                 X'20' -> X'7E'
 *               - blanks.
 *
 *   KEYENCD1, : Specifies how the label for the key encrypting key
 *   KEYENCD2    specified by the key label (input) is encoded by
 *               the key manager and stored on the tape cartridge.
 *
 *                Possible Values :
 *
 *                L      -> encoded as the specified label;
 *                H      -> encoded as a hash of the public key;
 *                blank  -> Not specified
 *
 *    EATTR    : Indicates whether the data set can support extended
 *               attributes.
 *
 *               Possible values:
 *
 *               O     -> Extended attributes are optional;
 *               N     -> No extended attributes, default for nonVSAM
 *                        data sets;
 *               blank -> Not specified;
 *
 *    RECLAIMCA: Specify whether or not the DASD space for empty CAs
 *               will be reclaimed on z/OS 1.12 or later systems.
 *
 *               Possible values:
 *
 *               Y     -> Yes, reclaim free CAs;
 *               N     -> No, do not reclaim free CAs;
 *               BLANK -> Default Y is assumed.
 *
 *    LOGREPL  : Specify whether or not the data set will be
 *               eligible for replication.
 *
 *               Possible values:
 *
 *               Y -> Yes, data set is eligible for VSAM replication
 *               N -> No, data set is not eligible for VSAM
 *                    replication (default)
 *
 *    RMODE31  : Specify where the buffers and control blocks
 *               are to reside.
 *
 *               Possible values:
 *
 *               ALL   -> Buffers and control blocks reside above
 *                        the line
 *               BUFF  -> Buffers only reside above the line
 *               CB    -> Control blocks reside above the line
 *               NONE  -> Buffers and control blocks reside below
 *                        the line
 *               blank -> Not specified
 *
 *    UPDHLVLSCDS: When modifying an SCDS, that was formatted with a
 *                 higher level of SMS, using a lower level of SMS
 *                 will make this application fail unless you
 *                 specify the UPDHLVLSCDS parameter as 'Y'.
 *                 Default is 'N'.
 *
 *                 Possible values : Y/N/BLANK
 *
 */

@SuppressWarnings("unused")
public class DataClass {
    private String scds;
    private String dcname;
    private String descr;
    private String recorg;
    private String recfm;
    private String lrecl;
    private String keylen;
    private String keyoff;
    private String spcavrec;
    private String spcavval;
    private String spcprm;
    private String spcsec;
    private String spcdir;
    private String override;
    private String rexppdt;
    private String volcnt;
    private String addvolam;
    private String ciszdata;
    private String frspcci;
    private String frspcca;
    private String shrxreg;
    private String shrxsys;
    private String comptn;
    private String mdtype;
    private String rectech;
    private String pefsclg;
    private String perfseg;
    private String dsnmtyp;
    private String ifext;
    private String extaddrs;
    private String recaccb;
    private String reuse;
    private String iniload;
    private String spanonsp;
    private String bwo;
    private String log;
    private String logstid;
    private String spcconrl;
    private String redspcut;
    private String blkszlim;
    private String frlog;
    private String rlscf;
    private String maxvol;
    private String extcons;
    private String rlsabove;
    private String smbvsp;
    private String sdb;
    private String keylabl1;
    private String keyencd1;
    private String keylabl2;
    private String keyencd2;
    private String eattr;
    private String reclaimca;
    private String logrepl;
    private String rmode31;
    private String updhlvlscds;

    public String getScds() {
        return scds;
    }

    public void setScds(String scds) {
        this.scds = scds;
    }

    public String getDcname() {
        return dcname;
    }

    public void setDcname(String dcname) {
        this.dcname = dcname;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getRecorg() {
        return recorg;
    }

    public void setRecorg(String recorg) {
        this.recorg = recorg;
    }

    public String getRecfm() {
        return recfm;
    }

    public void setRecfm(String recfm) {
        this.recfm = recfm;
    }

    public String getLrecl() {
        return lrecl;
    }

    public void setLrecl(String lrecl) {
        this.lrecl = lrecl;
    }

    public String getKeylen() {
        return keylen;
    }

    public void setKeylen(String keylen) {
        this.keylen = keylen;
    }

    public String getKeyoff() {
        return keyoff;
    }

    public void setKeyoff(String keyoff) {
        this.keyoff = keyoff;
    }

    public String getSpcavrec() {
        return spcavrec;
    }

    public void setSpcavrec(String spcavrec) {
        this.spcavrec = spcavrec;
    }

    public String getSpcavval() {
        return spcavval;
    }

    public void setSpcavval(String spcavval) {
        this.spcavval = spcavval;
    }

    public String getSpcprm() {
        return spcprm;
    }

    public void setSpcprm(String spcprm) {
        this.spcprm = spcprm;
    }

    public String getSpcsec() {
        return spcsec;
    }

    public void setSpcsec(String spcsec) {
        this.spcsec = spcsec;
    }

    public String getSpcdir() {
        return spcdir;
    }

    public void setSpcdir(String spcdir) {
        this.spcdir = spcdir;
    }

    public String getOverride() {
        return override;
    }

    public void setOverride(String override) {
        this.override = override;
    }

    public String getRexppdt() {
        return rexppdt;
    }

    public void setRexppdt(String rexppdt) {
        this.rexppdt = rexppdt;
    }

    public String getVolcnt() {
        return volcnt;
    }

    public void setVolcnt(String volcnt) {
        this.volcnt = volcnt;
    }

    public String getAddvolam() {
        return addvolam;
    }

    public void setAddvolam(String addvolam) {
        this.addvolam = addvolam;
    }

    public String getCiszdata() {
        return ciszdata;
    }

    public void setCiszdata(String ciszdata) {
        this.ciszdata = ciszdata;
    }

    public String getFrspcci() {
        return frspcci;
    }

    public void setFrspcci(String frspcci) {
        this.frspcci = frspcci;
    }

    public String getFrspcca() {
        return frspcca;
    }

    public void setFrspcca(String frspcca) {
        this.frspcca = frspcca;
    }

    public String getShrxreg() {
        return shrxreg;
    }

    public void setShrxreg(String shrxreg) {
        this.shrxreg = shrxreg;
    }

    public String getShrxsys() {
        return shrxsys;
    }

    public void setShrxsys(String shrxsys) {
        this.shrxsys = shrxsys;
    }

    public String getComptn() {
        return comptn;
    }

    public void setComptn(String comptn) {
        this.comptn = comptn;
    }

    public String getMdtype() {
        return mdtype;
    }

    public void setMdtype(String mdtype) {
        this.mdtype = mdtype;
    }

    public String getRectech() {
        return rectech;
    }

    public void setRectech(String rectech) {
        this.rectech = rectech;
    }

    public String getPefsclg() {
        return pefsclg;
    }

    public void setPefsclg(String pefsclg) {
        this.pefsclg = pefsclg;
    }

    public String getPerfseg() {
        return perfseg;
    }

    public void setPerfseg(String perfseg) {
        this.perfseg = perfseg;
    }

    public String getDsnmtyp() {
        return dsnmtyp;
    }

    public void setDsnmtyp(String dsnmtyp) {
        this.dsnmtyp = dsnmtyp;
    }

    public String getIfext() {
        return ifext;
    }

    public void setIfext(String ifext) {
        this.ifext = ifext;
    }

    public String getExtaddrs() {
        return extaddrs;
    }

    public void setExtaddrs(String extaddrs) {
        this.extaddrs = extaddrs;
    }

    public String getRecaccb() {
        return recaccb;
    }

    public void setRecaccb(String recaccb) {
        this.recaccb = recaccb;
    }

    public String getReuse() {
        return reuse;
    }

    public void setReuse(String reuse) {
        this.reuse = reuse;
    }

    public String getIniload() {
        return iniload;
    }

    public void setIniload(String iniload) {
        this.iniload = iniload;
    }

    public String getSpanonsp() {
        return spanonsp;
    }

    public void setSpanonsp(String spanonsp) {
        this.spanonsp = spanonsp;
    }

    public String getBwo() {
        return bwo;
    }

    public void setBwo(String bwo) {
        this.bwo = bwo;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLogstid() {
        return logstid;
    }

    public void setLogstid(String logstid) {
        this.logstid = logstid;
    }

    public String getSpcconrl() {
        return spcconrl;
    }

    public void setSpcconrl(String spcconrl) {
        this.spcconrl = spcconrl;
    }

    public String getRedspcut() {
        return redspcut;
    }

    public void setRedspcut(String redspcut) {
        this.redspcut = redspcut;
    }

    public String getBlkszlim() {
        return blkszlim;
    }

    public void setBlkszlim(String blkszlim) {
        this.blkszlim = blkszlim;
    }

    public String getFrlog() {
        return frlog;
    }

    public void setFrlog(String frlog) {
        this.frlog = frlog;
    }

    public String getRlscf() {
        return rlscf;
    }

    public void setRlscf(String rlscf) {
        this.rlscf = rlscf;
    }

    public String getMaxvol() {
        return maxvol;
    }

    public void setMaxvol(String maxvol) {
        this.maxvol = maxvol;
    }

    public String getExtcons() {
        return extcons;
    }

    public void setExtcons(String extcons) {
        this.extcons = extcons;
    }

    public String getRlsabove() {
        return rlsabove;
    }

    public void setRlsabove(String rlsabove) {
        this.rlsabove = rlsabove;
    }

    public String getSmbvsp() {
        return smbvsp;
    }

    public void setSmbvsp(String smbvsp) {
        this.smbvsp = smbvsp;
    }

    public String getSdb() {
        return sdb;
    }

    public void setSdb(String sdb) {
        this.sdb = sdb;
    }

    public String getKeylabl1() {
        return keylabl1;
    }

    public void setKeylabl1(String keylabl1) {
        this.keylabl1 = keylabl1;
    }

    public String getKeyencd1() {
        return keyencd1;
    }

    public void setKeyencd1(String keyencd1) {
        this.keyencd1 = keyencd1;
    }

    public String getKeylabl2() {
        return keylabl2;
    }

    public void setKeylabl2(String keylabl2) {
        this.keylabl2 = keylabl2;
    }

    public String getKeyencd2() {
        return keyencd2;
    }

    public void setKeyencd2(String keyencd2) {
        this.keyencd2 = keyencd2;
    }

    public String getEattr() {
        return eattr;
    }

    public void setEattr(String eattr) {
        this.eattr = eattr;
    }

    public String getReclaimca() {
        return reclaimca;
    }

    public void setReclaimca(String reclaimca) {
        this.reclaimca = reclaimca;
    }

    public String getLogrepl() {
        return logrepl;
    }

    public void setLogrepl(String logrepl) {
        this.logrepl = logrepl;
    }

    public String getRmode31() {
        return rmode31;
    }

    public void setRmode31(String rmode31) {
        this.rmode31 = rmode31;
    }

    public String getUpdhlvlscds() {
        return updhlvlscds;
    }

    public void setUpdhlvlscds(String updhlvlscds) {
        this.updhlvlscds = updhlvlscds;
    }
}
