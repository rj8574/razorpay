package com.ritik.razorpay.common.utils;

import java.security.SecureRandom;
import java.util.Base64;


public class RandomizerUtil {
    public static SecureRandom Secure_Random = new SecureRandom();

    public static String randomBase64(int length) {
        byte[] buf = new byte[length * 3 / 4];
        Secure_Random.nextBytes(buf);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}

