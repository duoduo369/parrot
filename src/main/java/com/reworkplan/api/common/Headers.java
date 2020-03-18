package com.reworkplan.api.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Headers {

    public static final String USER_AGENT = "USER-AGENT";
    public static final String AUTHORIZATION = "Authorization";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String REFERER = "Referer";
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private Headers() {}

    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> requestHeaders = new HashMap<>();
        for (String headerName : Collections.list(request.getHeaderNames())) {
            requestHeaders.put(headerName, request.getHeader(headerName));
        }
        requestHeaders.put("request_ip", getClientIpAddress(request));
        return requestHeaders;
    }

    private static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
