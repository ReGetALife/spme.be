package com.spme.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Storage administration tasks that are performed using ISMF options can also be done in batch with JCL, CLISTs,
 * and REXX EXECs that are provided by NaviQuest.
 * Sample JCL can be found in the SYS1.SACBCNTL library
 * z/OS version: V2R1
 * Ref: https://www.ibm.com/support/knowledgecenter/SSLTBW_2.1.0/com.ibm.zos.v2r1.idas200/job.htm
 * @author Qingguo Li
 */
@Service
public class SmsService {
    @Resource
    private JclService js;


}
