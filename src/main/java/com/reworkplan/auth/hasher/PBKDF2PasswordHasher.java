package com.reworkplan.auth.hasher;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PBKDF2PasswordHasher implements PasswordHasher {
    public final Integer DEFAULT_ITERATIONS = 36000;
    public final String algorithm = "pbkdf2_sha256";

    public PBKDF2PasswordHasher() {}

    public String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    public String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    public String encode(String password, String salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return this.encode(password, salt, this.DEFAULT_ITERATIONS);
    }

    public boolean checkPassword(String password, String hashedPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }
}
