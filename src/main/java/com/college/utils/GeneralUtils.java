package com.college.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneralUtils {

    public static String hash(String username, String password) {
        String source = username + password;
        try {
            return DatatypeConverter.printHexBinary(
                    MessageDigest.getInstance("SHA-256").digest(source.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }




}
