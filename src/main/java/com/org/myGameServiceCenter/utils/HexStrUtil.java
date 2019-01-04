/**
 *
 */
package com.org.myGameServiceCenter.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class HexStrUtil {

    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data byte[]
     * @return 十六进制char[]
     */
    public static String encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * @param data
     * @param offset
     * @param len
     * @return
     */
    public static String encodeHex(byte[] data, int offset, int len) {
        return encodeHex(data, offset, len, true);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制char[]
     */
    public static String encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * @param data
     * @param offset
     * @param len
     * @param toLowerCase
     * @return
     */
    public static String encodeHex(byte[] data, int offset, int len, boolean toLowerCase) {
        return encodeHex(data, offset, len, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制char[]
     */
    protected static String encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return String.valueOf(out);
    }

    /**
     * @param data
     * @param offset
     * @param len
     * @param toDigits
     * @return
     */
    protected static String encodeHex(byte[] data, int offset, int len, char[] toDigits) {
        int l = len;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i + offset]) >>> 4];
            out[j++] = toDigits[0x0F & data[i + offset]];
        }
        return String.valueOf(out);
    }

    private static String ASCIIToConvert(List<String> strs) {
        StringBuffer result = new StringBuffer();
        for (String str : strs) {
            result.append((char) Integer.parseInt(str, 16));
        }
        return new String(result);
    }

    static public String formatNumData(String str, String other) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return Integer.valueOf(str, 16) + other;
    }

    static public String formatAsciiData(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        List<String> result = new ArrayList<>();
        int length = str.length() / 2;
        for (int i = 0; i < length; i++) {
            String template = str.substring(i * 2, (i + 1) * 2);
            result.add(template);
        }
        return ASCIIToConvert(result);
    }

    /***********
     * 十进制字符串变成二进制字符串,二进制字符串左补0
     * @param str
     * @return
     */
    static public String decStr2BinaryStr(String str) {
        int i = Integer.valueOf(str, 10);
        return StringUtils.leftPad(Integer.toBinaryString(i), 8, "0");
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data byte[]
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制String
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param hexStr 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    public static byte[] decodeHex(String hexStr) {
        byte[] data = hexStr.getBytes();
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    public static int toDigit(byte ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    public static String toChineseHex(String s) {

        String ss = s;
        byte[] bt = ss.getBytes();
        String s1 = "";
        for (int i = 0; i < bt.length; i++) {
            String tempStr = Integer.toHexString(bt[i]);
            if (tempStr.length() > 2) {
                tempStr = tempStr.substring(tempStr.length() - 2);
            }
            s1 = s1 + tempStr;
        }
        return s1.toUpperCase();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            System.out.println(buf[i] & 0xFF);
            String hex = Integer.toHexString(buf[i] & 0xFF);
            System.out.println(hex);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static Integer fiveArrayToInteger(byte[] result) {
        int a = result[0] << 0;
        int b = result[1] << 1;
        int c = result[2] << 2;
        int d = result[3] << 3;
        int e = result[4] << 4;
        int f = result[5] << 5;
        return a + b + c + d + e + f;
    }

    /******
     *
     * @param str
     * @return
     */
    public static String integerStr2HexString(String str, int length) {
        try {
            int temple = Integer.valueOf(str);
            String result = StringUtils.leftPad(Integer.toHexString(temple), length, "0");
            return result;
        } catch (Exception exception) {
            return null;
        }
    }

    public static void main(String[] args) {
//        String srcStr = "1";
//        String encodeStr = encodeHexStr(srcStr.getBytes());
//        String decodeStr = new String(decodeHex(encodeStr));
//        System.out.println("before encode:" + srcStr);
//        System.out.println("after encode:" + encodeStr);
//        System.out.println("convert:" + decodeStr);
        System.out.println("111111             " + encodeHex("12324565.".getBytes()));
        System.out.println("222222             " + integerStr2HexString("5556", 4));
        System.out.println("333333             " + integerStr2HexString("0000", 4));
        System.out.println("333333             " + integerStr2HexString("01", 2));

//        byte[] array = new byte[]{1, 1, 1, 1, 1};
//        System.out.println(fiveArrayToInteger(array));
    }
}
