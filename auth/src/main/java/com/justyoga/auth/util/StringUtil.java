package com.justyoga.auth.util;

import com.google.common.base.CaseFormat;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
    public static Boolean isBlank(String string) {
        return StringUtils.isBlank(string);
    }

    public static String getBase64ForString(String str) {
        if (str == null || str.isEmpty()) return "";
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String getStringFromBase64(String str) {
        if (str == null || str.isEmpty()) return "";
        try {
            byte[] asBytes = Base64.getDecoder().decode(str);
            return new String(asBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String createUniqueIdFromStringName(String str) {
        if (str == null || str.isEmpty()) return str;
        String spaceSafeStr = removeSpace(str);
        String camelCaseToHyphenSeparated = camelCaseToHyphenSeparated(spaceSafeStr);
        return UUID.randomUUID().toString() + "-" + camelCaseToHyphenSeparated;
    }

    public static String removeSpace(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.replaceAll("\\s", "");
    }

    public static String camelCaseToHyphenSeparated(String str) {
        if (str == null || str.isEmpty()) return str;
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, str);
    }
}
