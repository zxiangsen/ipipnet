/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.musn.ipipnet;

import java.util.StringTokenizer;

import com.lamfire.logger.Logger;

/**
 * @author zxc Mar 7, 2017 12:17:18 PM
 */
public class Helper {

    private static final Logger LOGGER = Logger.getLogger(Helper.class);

    public static byte[] encode(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ret;
    }

    public static String decode(byte[] ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    public static int compareIP(byte[] ip, byte[] beginIp) {
        for (int i = 0; i < 4; ++i) {
            int r = compareByte(ip[i], beginIp[i]);
            if (r != 0) return r;
        }
        return 0;
    }

    private static int compareByte(byte b1, byte b2) {
        if ((b1 & 0xFF) > (b2 & 0xFF)) return 1;
        if ((b1 ^ b2) == 0) return 0;
        return -1;
    }
}
