package com.spme.utils;

import com.spme.domain.JobInfo;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * Methods to communicate with z/OSMF
 *
 * @author Qingguo Li
 */
public class ZosmfUtil {

    private static HttpComponentsClientHttpRequestFactory requestFactory;

    // disable check of SSL Cert
    static {
        CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
        requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
    }

    /**
     * issue an http request to s/OSMF
     *
     * @param session      http session
     * @param path         path of z/OSMF API( with params maybe ) starting with "/"
     * @param method       http method
     * @param responseType class type of response
     * @param body         body of request. it can be null
     * @return response entity of class type T
     */
    public static <T> T go(HttpSession session, String path, HttpMethod method, Object body, HttpHeaders headers, Class<T> responseType) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        String urlOverHttps = "https://" + ZOSMF_Address.toString() + path;

        // set header with jsessionid and token
        if (headers == null) {
            // headers fallback
            headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
        }
        headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        return new RestTemplate(requestFactory).exchange(urlOverHttps, method, request, responseType).getBody();
    }

    /**
     * polling for job status
     *
     * @param session http session
     * @param path    path of z/OSMF API( with params maybe )
     * @param seconds polling for how many seconds
     * @return true if job is ready in giving time, otherwise false
     */
    public static boolean isReady(HttpSession session, String path, int seconds) {
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");

        String urlOverHttps = "https://" + ZOSMF_Address.toString() + path;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("Cookie", ZOSMF_JSESSIONID.toString() + ";" + ZOSMF_LtpaToken2);
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);// millisecond
            } catch (Exception e) {
                e.printStackTrace();
            }
            ResponseEntity<JobInfo> response = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.GET, request, JobInfo.class);
            if (response.getBody() != null && response.getBody().getStatus().equals("OUTPUT")) {
                return true;
            }
        }
        return false;
    }
}
