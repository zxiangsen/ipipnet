/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.musn.ipipnet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.FileUtils;
import com.lamfire.utils.StringUtils;

/**
 * @author zxc Mar 7, 2017 12:16:52 PM
 */
public class IpTools {

    private static final Logger LOGGER   = Logger.getLogger(IpTools.class);
    private static File         dataFile = null;
    private static List<String> lines    = null;

    static {
        init();
    }

    private static void init() {
        String ipDatFileName = System.getProperty("ipdat");
        if (StringUtils.isNotEmpty(ipDatFileName)) {
            dataFile = new File(ClassLoaderUtils.getResource(ipDatFileName, IpTools.class).getPath());
        } else {
            dataFile = new File(ClassLoaderUtils.getResource("ipipnet.txt", IpTools.class).getPath());
        }
        if ((!(dataFile.exists())) && (!(dataFile.canRead()))) {
            LOGGER.error("IP地址信息文件读取失败");
        }
        try {
            lines = FileUtils.readLines(dataFile);
        } catch (IOException e) {
            LOGGER.error("IP地址信息文件读取失败", e);
        }
    }

    public static IpNetProvider getNetProvider(String ip) {
        if ((dataFile == null) || (lines == null)) {
            return null;
        }
        return getNetProvider(Helper.encode(ip));
    }

    @SuppressWarnings("unused")
    private static IpNetProvider getNetProvider(byte[] ip) {
        int maxIndex = lines.size() - 1;
        int minIndex = 0;
        int middleIndex = 0;
        String[] ipinfo = null;
        byte[] ipBegin = null;
        byte[] ipEnd = null;

        String res = null;
        for (int i = 0; i < 25; ++i) {
            middleIndex = (maxIndex + minIndex) / 2;
            ipinfo = ((String) lines.get(middleIndex)).split("\t");
            ipBegin = Helper.encode(ipinfo[0]);
            ipEnd = Helper.encode(ipinfo[1]);

            int rb = Helper.compareIP(ip, ipBegin);
            int re = Helper.compareIP(ip, ipEnd);
            if ((rb == 0) || (re == 0) || ((rb > 0) && (re < 0))) {
                res = (String) lines.get(middleIndex);
                break;
            }
            if (rb < 0) {
                maxIndex = middleIndex;
            } else {
                minIndex = middleIndex;
            }
        }
        return IpNetProvider.parser(Helper.decode(ip), (String) lines.get(middleIndex));
    }
}
