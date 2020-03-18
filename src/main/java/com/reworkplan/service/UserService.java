package com.reworkplan.service;

import com.reworkplan.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
