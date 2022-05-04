package com.mkyong.crypto.hash;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.*;

public class ShaUtils {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getSha(String input) {

        //String algorithm = "SHA-256"; // if you perfer SHA-2
        String algorithm = "SHA3-256";

        byte[] shaInBytes = ShaUtils.digest(input.getBytes(UTF_8), algorithm);
        return bytesToHex(shaInBytes);
        // fixed length, 32 bytes, 256 bits.

    }

    public static String hexToString(String input) {
        byte[] bytes = null;
        try {
            bytes = Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            e.getCause();
        }
        String FinishedString = null;
        try {
            FinishedString = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return FinishedString;
    }
}