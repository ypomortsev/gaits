package com.pomortsev.gaits;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String getUriHost(String uri) {
        if (uri.startsWith("http")) {
            uri = StringUtils.substringAfter(uri, "://");
        }

        uri = StringUtils.substringBefore(uri, "/");
        uri = StringUtils.substringBefore(uri, ":");

        return uri;
    }
}
