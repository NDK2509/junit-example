package org.example.services;

import lombok.AllArgsConstructor;
import org.example.models.AuthData;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.services.interfaces.IAuthenticationService;
import org.example.utils.TokenUtils;

@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private UserRepository userRepository;

    @Override
    public AuthData authenticate(String email, String password) { // Login
        User user = userRepository.getByEmail(email);

        if (user == null || !user.getPassword().equals(password)) return null;
        return new AuthData(
                user,
                TokenUtils.generate() // accessToken
        );
    }
}
