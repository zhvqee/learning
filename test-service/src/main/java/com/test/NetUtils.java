package com.test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtils {

    public static InetAddress getLocalInetAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {//遍历枚举中的每一个元素
                NetworkInterface ni = en.nextElement();
                Enumeration<InetAddress> enumInetAddr = ni.getInetAddresses();
                while (enumInetAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddr.nextElement();
                    // 排除loopback回环类型地址和链路本地地址
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        return inetAddress;
                    }
                }
            }
        } catch (Exception e) {

        }
        return null;
    }
}
