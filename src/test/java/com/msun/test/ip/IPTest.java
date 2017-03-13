/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.test.ip;

import com.musn.ipipnet.IpNetProvider;
import com.musn.ipipnet.IpTools;

/**
 * @author zxc Mar 13, 2017 2:17:27 PM
 */
public class IPTest {

    public static void main(String[] args) {
        IpNetProvider np = IpTools.getNetProvider("223.104.3.205");
        System.out.println(np);
    }
}
