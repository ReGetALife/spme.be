package com.spme.service;

import com.spme.domain.DatasetInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Qingguo Li
 */
@Service
public class RexxService {

    @Resource
    private DatasetService ds;

    @Resource
    private CmdService cs;

    /**
     * run a rexx script
     * store rexx code in a dataset then run it through JCL
     */
    public String runRexx(HttpSession session, String code, String params) {
        String uid = session.getAttribute("ZOSMF_Account").toString();
        String datasetName;

        // create a unique name
        int count;
        do {
            datasetName = (uid + ".SPME.REXXTEMP." + RandomStringUtils.randomAlphabetic(8)).toUpperCase();
            count = ds.getDatasetList(session, datasetName).size();
        } while (count > 0);
        // create a dataset
        DatasetInfo datasetInfo = new DatasetInfo();
        datasetInfo.setDsname(datasetName);
        datasetInfo.setUnit("3390");
        datasetInfo.setDsorg("PS");
        datasetInfo.setAlcunit("TRK");
        datasetInfo.setPrimary(10);
        datasetInfo.setSecondary(5);
        datasetInfo.setAvgblk(500);
        datasetInfo.setRecfm("FB");
        datasetInfo.setBlksize(400);
        datasetInfo.setLrecl(80);
        if (!ds.createDataset(session, datasetInfo)) {
            return null;
        }
        // ignore whether writing dataset succeeded or not
        ds.writeDataset(session, datasetName, code);
        // command
        String command = "EXEC '" + datasetName + "' '" + params + "' EXEC";
        // run REXX code through tso command
        String res = cs.runCMD(session, command);
        // delete the dataset, ignore whether succeeded
        ds.deleteDataset(session, datasetName);

        System.out.println(datasetName);

        // res can be null
        return res;
    }
}
