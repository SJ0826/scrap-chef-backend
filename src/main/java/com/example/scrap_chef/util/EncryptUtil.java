package com.example.scrap_chef.util;

import com.example.scrap_chef.code.error.CommonError;
import com.example.scrap_chef.exception.BadRequestException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static String encryptSha512(String str) {
        return encryptHash("SHA-512", str);
    }

    // MD5, SHA-256, SHA-512
    private static String encryptHash(String algorithm, String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(str.getBytes("UTF-8"));
            byte[] encrypted = md.digest();
            return byteArrayToHex(encrypted);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new BadRequestException(CommonError.ENCRYPT_FAIL);
        }
    }

    // byte array 저장을 위한 hex 처리
    public static String byteArrayToHex(byte[] value) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : value) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
