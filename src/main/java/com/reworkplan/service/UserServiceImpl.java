package com.reworkplan.service;

import com.google.inject.Inject;
import com.reworkplan.auth.hasher.PasswordHasher;
import com.reworkplan.mappers.UserMapper;
import com.reworkplan.models.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final PasswordHasher passwordHasher;

    @Inject
    public UserServiceImpl(UserMapper userMapper, PasswordHasher passwordHasher) {
        this.userMapper = userMapper;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Optional<User> login(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = userMapper.selectByUsername(username);
        Boolean correct = passwordHasher.checkPassword(password, user.getPassword());
        if (correct){
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }
}
