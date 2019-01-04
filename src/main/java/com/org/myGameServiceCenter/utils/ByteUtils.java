package com.org.myGameServiceCenter.utils;

public class ByteUtils {

    public static String reverseString(String str) {
        byte[] inputStrByte = str.getBytes();
        for (int i = 0; i < inputStrByte.length / 2; i++) {
            byte temp = inputStrByte[i];
            inputStrByte[i] = inputStrByte[inputStrByte.length - i - 1];
            inputStrByte[inputStrByte.length - i - 1] = temp;
        }
        return new String(inputStrByte);
    }

}
