package com.example.icx;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHasher {
    public static String hashPassword(String password) {
        // use the same salt and iterations as in your database
        String salt = "41iS1csjz44d4da1GhxFsv";
        int iterations = 390000;
        // convert the salt to bytes
        byte[] saltBytes = salt.getBytes();
        // create a key specification for PBKDF2 with the same salt and iterations
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, 256);
        // use a secret key factory to generate a hash of the password
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashBytes = skf.generateSecret(spec).getEncoded();
            // encode the hash as a string and return it
            return "pbkdf2_sha256$" + iterations + "$" + salt + "$" + base64Encode(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // handle exceptions here
            return null;
        }
    }

    private static String base64Encode(byte[] bytes) {
        // implement base64 encoding here
        return null;
    }
}