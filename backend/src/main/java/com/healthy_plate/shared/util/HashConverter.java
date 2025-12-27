package com.healthy_plate.shared.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashConverter {

    public static String convertToHash(final String value) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            final StringBuilder hexBuilder = new StringBuilder();
            for (byte b : hash) {
                final String hexString = Integer.toHexString(0xff & b);
                if (hexString.length() == 1) {
                    hexBuilder.append('0');
                }
                hexBuilder.append(hexString);
            }
            return hexBuilder.toString().substring(0, 16);
        } catch (Exception e) {
            throw new RuntimeException("해쉬값 생성 실패");
        }
    }
}
