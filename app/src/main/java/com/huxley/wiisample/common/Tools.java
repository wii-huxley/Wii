package com.huxley.wiisample.common;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huxley on 2017/9/16.
 */

public class Tools {

    private static final int[] base64DecodeChars = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    public static boolean isChinese1(char a) {
        return a >= 19968 && a <= 171941;
    }

    public static boolean isChinese2(char a) {
        return String.valueOf(a).matches("[一-龥]");
    }

    public static String getCenterString(String start, String end, String content) {
        Pattern pattern1 = Pattern.compile(String.format(".*?\\%s(.*?)\\%s.*?", start, end));
        Matcher matcher1 = pattern1.matcher(content);
        return matcher1.matches() ? matcher1.group(1) : content;
    }

    public static String base64decode(String str) {
        int len = str.length();
        int i = 0;
        char c3;
        char c4;
        String out;
        for (out = ""; i < len; out = out + (char) ((c3 & 3) << 6 | c4)) {
            char c1;
            do {
                c1 = (char) base64DecodeChars[str.charAt(i++) & 255];
            } while (i < len && c1 == -1);
            if (c1 == -1) {
                break;
            }
            char c2;
            do {
                c2 = (char) base64DecodeChars[str.charAt(i++) & 255];
            } while (i < len && c2 == -1);
            if (c2 == -1) {
                break;
            }
            out = out + (char) (c1 << 2 | (c2 & 48) >> 4);
            do {
                c3 = (char) (str.charAt(i++) & 255);
                if (c3 == 61) {
                    return out;
                }
                c3 = (char) base64DecodeChars[c3];
            } while (i < len && c3 == -1);
            if (c3 == -1) {
                break;
            }
            out = out + (char) ((c2 & 15) << 4 | (c3 & 60) >> 2);
            do {
                c4 = (char) (str.charAt(i++) & 255);
                if (c4 == 61) {
                    return out;
                }
                c4 = (char) base64DecodeChars[c4];
            } while (i < len && c4 == -1);
            if (c4 == -1) {
                break;
            }
        }
        return out;
    }

    public static String urlEncode(String content, String charsetName) {
        try {
            return URLEncoder.encode(content, charsetName);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static String base64encode(String str) {
        int i = 0;
        int len = str.length();
        char c3;
        String out;
        for(out = ""; i < len; out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c3 & 63)) {
            int c1 = str.charAt(i++) & 255;
            if(i == len) {
                out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c1 >> 2);
                out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((c1 & 3) << 4);
                out = out + "==";
                break;
            }
            char c2 = str.charAt(i++);
            if(i == len) {
                out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c1 >> 2);
                out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((c1 & 3) << 4 | (c2 & 240) >> 4);
                out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((c2 & 15) << 2);
                out = out + "=";
                break;
            }
            c3 = str.charAt(i++);
            out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c1 >> 2);
            out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((c1 & 3) << 4 | (c2 & 240) >> 4);
            out = out + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((c2 & 15) << 2 | (c3 & 192) >> 6);
        }
        return out;
    }

    public static String utf16to8(String str) {
        int i = 0;
        int len = str.length();

        String out;
        for(out = ""; i < len; ++i) {
            char c = str.charAt(i);
            if(c >= 1 && c <= 127) {
                out = out + str.charAt(i);
            } else if(c > 2047) {
                out = out + (char)(224 | c >> 12 & 15);
                out = out + (char)(128 | c >> 6 & 63);
                out = out + (char)(128 | c & 63);
            } else {
                out = out + (char)(192 | c >> 6 & 31);
                out = out + (char)(128 | c & 63);
            }
        }

        return out;
    }


    public static String utf8to16(String str) {
        int i = 0;
        int len = str.length();
        String out = "";

        while(i < len) {
            char c = str.charAt(i++);
            char char2;
            switch(c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    out = out + str.charAt(i - 1);
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    break;
                case 12:
                case 13:
                    char2 = str.charAt(i++);
                    out = out + (char)((c & 31) << 6 | char2 & 63);
                    break;
                case 14:
                    char2 = str.charAt(i++);
                    char char3 = str.charAt(i++);
                    out = out + (char)((c & 15) << 12 | (char2 & 63) << 6 | char3 & 63);
            }
        }
        return out;
    }

    public static String urlDecode(String content, String charsetName) {
        try {
            return URLDecoder.decode(content, charsetName);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return "";
        }
    }



    public static boolean checkIsInstall(Context paramContext, String paramString) {
        if(paramString != null && !"".equals(paramString)) {
            try {
                paramContext.getPackageManager().getApplicationInfo(paramString, 0);
                return true;
            } catch (PackageManager.NameNotFoundException var3) {
                return false;
            }
        } else {
            return false;
        }
    }
}
