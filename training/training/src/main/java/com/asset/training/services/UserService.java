package com.asset.training.services;

import com.asset.training.constants.ErrorCodes;
import com.asset.training.daos.UserDao;
import com.asset.training.exceptions.TrainingException;
import com.asset.training.models.LoginModel;
import com.asset.training.models.UserModel;
import com.asset.training.security.JWTProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    private final JWTProvider jwtProvider;

    public UserService(UserDao userDao, JWTProvider jwtProvider) {
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
    }

    public String login(LoginModel loginRequest) {
        UserModel user = getByUsername(loginRequest.getUsername());
        if (user == null || !loginRequest.getPassword().equals(user.getPassword())) {
            throw new TrainingException(ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD, 1);
        }
        String token = jwtProvider.generateToken(user);
        return token;
    }

    public UserModel getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) getByUsername(username);
        return userDetails;
    }
}
