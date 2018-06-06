package com.pratiksymz.android.barcodeandblockchain.Utils;

import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by pratiksymz on 14/05/18.
 */

public class StringUtil {
    private static final String LOG_TAG = StringUtil.class.getSimpleName();
    // Applies Sha256 to a string and returns the result

    public static String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Applying SHA-256 to our input
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b: hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Encryption Error: " + e);
            throw new RuntimeException(e);
        }
    }
}
