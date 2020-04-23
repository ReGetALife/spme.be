package com.spme.controller;

import com.spme.domain.DatasetInfo;
import com.spme.service.DatasetService;
import com.spme.utils.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Qingguo Li
 */

@Controller
public class DatasetController {

    @Resource
    private DatasetService ds;

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
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getDatasetList(@RequestParam String pattern, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(ds.getDatasetList(session, pattern));
    }

    /**
     * get member list of a partitioned dataset
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset/{datasetName}/member", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getMemberList(@PathVariable String datasetName, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(ds.getMemberList(session, datasetName));
    }

    /**
     * get content of a sequential dataset or a partitioned dataset member
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> getContent(@PathVariable String name, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(ds.getContent(session, name));
    }

    /**
     * create a sequential or partitioned dataset
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset", method = RequestMethod.POST)
    public ResponseEntity<String> createDataset(@RequestBody DatasetInfo datasetInfo, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        if (ds.createDataset(session, datasetInfo)) {
            return ResponseEntity.ok("successful");
        }
        return ResponseEntity.status(500).body(null);
    }

    /**
     * write to a sequential dataset or partitioned dataset member
     * if the dataset member not exist, it will create that member
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset/{name}", method = RequestMethod.PUT)
    public ResponseEntity<String> writeDataset(@PathVariable String name, @RequestBody String content, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        if (ds.writeDataset(session, name, content)) {
            return ResponseEntity.ok("successful");
        }
        return ResponseEntity.status(500).body(null);
    }

    /**
     * delete a sequential or partitioned data set or a partitioned dataset member
     */
    @CrossOrigin(origins = "*", allowCredentials = "true")
    @RequestMapping(value = "/dataset/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDataset(@PathVariable String name, HttpSession session) {
        if (AuthUtil.notLogin(session)) {
            return ResponseEntity.status(401).body(null);
        }
        if (ds.deleteDataset(session, name)) {
            return ResponseEntity.ok("successful");
        }
        return ResponseEntity.status(500).body(null);
    }
}
