package com.reworkplan.auth.hasher;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PBKDF2PasswordHasherTest {
    private final PasswordHasher hasher = new PBKDF2PasswordHasher();

    @Test
    public void verifyTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String password = "123456";
        String encoded = "pbkdf2_sha256$36000$kyUlfaZJt1xL$hbeutQrjsK7qYlkL6X/AzNkzG4WHXkIM5e6M6DZvMxc=";
        assertTrue(hasher.checkPassword(password, encoded));
    }
}
