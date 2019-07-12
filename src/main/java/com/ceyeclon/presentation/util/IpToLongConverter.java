package com.ceyeclon.presentation.util;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class IpToLongConverter {

    public List<String> sortIpList(List<String> ips) {
        List<Long> numbericIp = ips.stream()
                .map(this::ipToLong)
                .sorted()
                .collect(Collectors.toList());
        return numbericIp.stream()
                .map(this::longToIp)
                .collect(Collectors.toList());
    }

    private long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }
        return result;
    }


    private String longToIp(long i) {
        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);

    }
}
