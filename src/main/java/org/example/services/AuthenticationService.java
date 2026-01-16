package org.example.services;

import lombok.AllArgsConstructor;
import org.example.dto.AuthData;
import org.example.dto.RegisterDTO;
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

    @Override
    public AuthData register(RegisterDTO registerDTO) {
        // Check email ton tai
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return null;
        }
        User user = User.fromRegisterDTO(registerDTO);
        userRepository.save(user);

        return new AuthData(user, TokenUtils.generate());
    }
}
