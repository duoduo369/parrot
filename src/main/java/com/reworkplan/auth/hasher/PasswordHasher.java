package com.reworkplan.auth.hasher;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PasswordHasher {
    String encode(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException;
    String encode(String password, String salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException;
    boolean checkPassword(String password, String encoded) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
