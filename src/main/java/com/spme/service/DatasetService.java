package com.spme.service;

import com.spme.utils.ZosmfUtil;
import org.springframework.http.HttpMethod;
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

    /**
     * get dataset list matching giving pattern
     */
    @SuppressWarnings("unchecked")
    public List<String> getDatasetList(HttpSession session, String datasetNamePattern) {
        List<Map<String, String>> list = (List<Map<String, String>>) ZosmfUtil.go(session, "/zosmf/restfiles/ds?dslevel=" + datasetNamePattern, HttpMethod.GET, null, Map.class).get("items");
        List<String> names = new ArrayList<>();
        for (Map<String, String> m : list) {
            names.add(m.get("dsname"));
        }
        return names;
    }

    /**
     * get member list of a partitioned dataset
     */
    @SuppressWarnings("unchecked")
    public List<String> getMemberList(HttpSession session, String datasetName) {
        List<Map<String, String>> list = (List<Map<String, String>>) ZosmfUtil.go(session, "/zosmf/restfiles/ds" + datasetName + "/member", HttpMethod.GET, null, Map.class).get("items");
        List<String> names = new ArrayList<>();
        for (Map<String, String> m : list) {
            names.add(m.get("member"));
        }
        return names;
    }
}
