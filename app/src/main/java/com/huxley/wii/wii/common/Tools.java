package com.huxley.wii.wii.common;

import com.huxley.wii.yl.common.utils.StringUtils;

/**
 * Created by huxley on 2017/2/12.
 */

public class Tools {

    public static int getBeatNum(String timeSignature) {
        int beatNum = 0;
        if (!StringUtils.isEmpty(timeSignature) || timeSignature.length() < 1) {
            try {
                beatNum = Integer.valueOf(String.valueOf(timeSignature.charAt(0)));
            } catch (Exception e) {
                beatNum = 0;
            }
        }
        return beatNum;
    }
}
