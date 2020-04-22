package com.spme.service;

import com.spme.domain.DatasetInfo;
import com.spme.utils.ZosmfUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Qingguo Li
 */
@Service
public class DatasetService {

    private static final String datasetApiPath = "/zosmf/restfiles/ds";

    /**
     * get dataset list matching giving pattern, each Map object refers to a dataset e.g.
     * {
     * "dsname": "ST000.SPME.TMPPAR",
     * "blksz": "400",
     * "catnm": "CATALOG.UCAT.STGRP",
     * "cdate": "2020/04/19",
     * "dev": "3390",
     * "dsntp": "PDS",
     * "dsorg": "PO",
     * "edate": "***None***",
     * "extx": "1",
     * "lrecl": "80",
     * "migr": "NO",
     * "mvol": "N",
     * "ovf": "NO",
     * "rdate": "2020/04/20",
     * "recfm": "FB",
     * "sizex": "10",
     * "spacu": "TRACKS",
     * "used": "10",
     * "vol": "BYWK00"
     * }
     * attributes shown above can be not contained in Map object except "dsname"
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> getDatasetList(HttpSession session, String datasetNamePattern) {
        List<Map<String, String>> datasetList = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            // set header to query more info about dataset
            headers.add("X-IBM-Attributes", "base");
            datasetList = (List<Map<String, String>>) ZosmfUtil.go(
                    session,
                    datasetApiPath + "?dslevel=" + datasetNamePattern,
                    HttpMethod.GET,
                    null,
                    headers,
                    Map.class).get("items");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datasetList;
    }

    /**
     * get member list of a partitioned dataset
     */
    @SuppressWarnings("unchecked")
    public List<String> getMemberList(HttpSession session, String datasetName) {
        List<String> names = new ArrayList<>();
        try {
            List<Map<String, String>> list = (List<Map<String, String>>) ZosmfUtil.go(session,
                    datasetApiPath + "/" + datasetName + "/member",
                    HttpMethod.GET,
                    null,
                    null,
                    Map.class).get("items");
            for (Map<String, String> m : list) {
                names.add(m.get("member"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * get content of a sequential dataset or a partitioned dataset member
     */
    public String getContent(HttpSession session, String name) {
        String s = "";
        try {
            s = ZosmfUtil.go(session, datasetApiPath + "/" + name,
                    HttpMethod.GET,
                    null,
                    null,
                    String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * create a sequential or partitioned dataset
     */
    public boolean createDataset(HttpSession session, DatasetInfo datasetInfo) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ZosmfUtil.go(session, datasetApiPath + "/" + datasetInfo.getDsname(),
                    HttpMethod.POST,
                    datasetInfo,
                    headers,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * write to a sequential dataset or partitioned dataset member
     * if the dataset member not exist, it will create that member
     */
    public boolean writeDataset(HttpSession session, String name, String content) {
        try {
            ZosmfUtil.go(session, datasetApiPath + "/" + name,
                    HttpMethod.PUT,
                    content,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * delete a sequential or partitioned data set or a partitioned dataset member
     */
    public boolean deleteDataset(HttpSession session, String name) {
        try {
            ZosmfUtil.go(session,
                    datasetApiPath + "/" + name,
                    HttpMethod.DELETE,
                    null,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
