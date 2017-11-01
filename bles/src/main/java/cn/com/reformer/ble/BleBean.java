package cn.com.reformer.ble;

import mutils.ByteUtils;

public class BleBean {
    public String name;
    public String nameServer;
    public String address;
    public int rssi = 0;
    public byte[] mac = new byte[9];

//    public ArrayList<Bean> beens;

    public BleBean(String name, String nameServer, String address, int rssi, byte[] mac) {
        this.nameServer = nameServer;
        this.name = name;
        this.address = address;
        this.rssi = rssi;
        this.mac = mac;
    }

    public BleBean(byte[] mac, String nameServer) {
        this.nameServer = nameServer;
        this.mac = mac;
    }

    public BleBean(String name, byte[] mac, int rssi, String address, String nameServer) {
        this.name = name;
        this.mac = mac;
        this.rssi = rssi;
        this.address = address;
        this.nameServer = nameServer;
    }

    public String getMacStr() {
        if (mac == null) {
            return null;
        }
        return ByteUtils.bytesToString(mac);
    }

//
//    public static String bytesToString(byte[] mac) {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < mac.length; i++) {
//            if (mac[i] < 0)
//                mac[i] += 256;
//            sb.append(padLeft(Integer.toHexString(mac[i]), 2));
//        }
//        return sb.toString().toUpperCase();
//    }
//
//    public static String padLeft(String str, int len) {
//        if (str.length() > 2)
//            str = str.substring(str.length() - 2);
//        String pad = "0000000000000000";
//        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
//    }
//
//    private static String bytePadLeft(String str, int len) {
//        if (str.length() > 2)
//            str = str.substring(str.length() - 2);
//        String pad = "0000000000000000";
//        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
//    }
}
