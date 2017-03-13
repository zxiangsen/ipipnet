/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.musn.ipipnet;

import com.lamfire.json.JSON;

/**
 * @author zxc Mar 7, 2017 12:16:36 PM
 */
public class IpNetProvider {

    private String addr;
    private String ipinfo;
    private String provider;
    private String country;
    private String province;
    private String city;

    public IpNetProvider(String addr, String ipinfo, String provider, String country, String province, String city) {
        this.addr = addr;
        this.ipinfo = ipinfo;
        this.provider = provider;
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public IpNetProvider copy() {
        return new IpNetProvider(this.addr, this.ipinfo, this.provider, this.country, this.province, this.city);
    }

    public static IpNetProvider parser(String addr, String ipinfo) {
        String[] splitFields = ipinfo.split("\t");
        for (int i = 0; i < splitFields.length; ++i) {
            splitFields[i] = splitFields[i].trim();
        }
        String provider = (splitFields[6].equals("*")) ? null : splitFields[6];
        String country = (splitFields[2].equals("*")) ? null : splitFields[2];
        String province = (splitFields[3].equals("*")) ? null : splitFields[3];
        String city = (splitFields[4].equals("*")) ? null : splitFields[4];
        if ((country != null) && (province != null) && (country.equals(province))) {
            province = null;
        }
        IpNetProvider ret = new IpNetProvider(addr, ipinfo, provider, country, province, city);
        return ret;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return this.addr;
    }

    public String getIpinfo() {
        return this.ipinfo;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getCountry() {
        return this.country;
    }

    public String getProvince() {
        return this.province;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
