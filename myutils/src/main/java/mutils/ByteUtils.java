package mutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class ByteUtils {
    //给出两个16进制字节,返回10进制int数字,高位在前,低位在后
    public static int bytes2int(byte a, byte b) {
//        int a0 = a;
//        int b0 = b;
        return (a << 8 & 0xFF00) | (b & 0xFF);
    }

    public static int bytes2int(byte[] res) {// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    //给int,返回byte数组,高位在前,低位在后
    public static byte[] int2bytes1(int tem) {
        byte b = (byte) (tem & 0xff);//低位
        byte a = (byte) (tem >> 8 & 0xff);//高位
        return new byte[]{a, b};
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] int2Bytes2(int value) {
        byte[] src = new byte[2];
//        src[3] =  (byte) ((value>>24) & 0xFF);
//        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static byte[] stringToBytes(String outStr) {//macString 2 macByte[]
        if (outStr.length() != 18)
            return null;
        byte[] mac = new byte[9];
        try {
            for (int i = 0; i < 9; i++) {
                String s = outStr.substring(i * 2, i * 2 + 2);
                if (Integer.valueOf(s, 16) > 0x7F) {
                    mac[i] = (byte) (Integer.valueOf(s, 16) - 0xFF - 1);
                } else {
                    mac[i] = Byte.valueOf(s, 16);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return mac;
    }

    public static String bytesToString(byte[] mac) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (mac[i] < 0)
                mac[i] += 256;
            sb.append(padLeft(Integer.toHexString(mac[i]), 2));
        }
        return sb.toString().toUpperCase();
    }

    private static String padLeft(String str, int len) {
        if (str.length() > 2)
            str = str.substring(str.length() - 2);
        String pad = "0000000000000000";
        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
//
//    /**
//     * 十六进制字符串转换成字符串
//     *
//     * @return String
//     */
//    public static String hexStr2Str(String hexStr) {
//
//        String str = "0123456789ABCDEF";
//        char[] hexs = hexStr.toCharArray();
//        byte[] bytes = new byte[hexStr.length() / 2];
//        int n;
//        for (int i = 0; i < bytes.length; i++) {
//            n = str.indexOf(hexs[2 * i]) * 16;
//            n += str.indexOf(hexs[2 * i + 1]);
//            bytes[i] = (byte) (n & 0xff);
//        }
//        return new String(bytes);
//    }
//
//
//    /**
//     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
//     */
//    public static byte[] intToBytes2(int value) {
//        byte[] src = new byte[4];
//        src[0] = (byte) ((value >> 24) & 0xFF);
//        src[1] = (byte) ((value >> 16) & 0xFF);
//        src[2] = (byte) ((value >> 8) & 0xFF);
//        src[3] = (byte) (value & 0xFF);
//        return src;
//    }
//
//    /**
//     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
//     *
//     * @param src    byte数组
//     * @param offset 从数组的第offset位开始
//     * @return int数值
//     */
//    public static int bytesToInt(byte[] src, int offset) {
//        int value;
//        value = (int) ((src[offset] & 0xFF)
//                | ((src[offset + 1] & 0xFF) << 8)
//                | ((src[offset + 2] & 0xFF) << 16)
//                | ((src[offset + 3] & 0xFF) << 24));
//        return value;
//    }
//
}
