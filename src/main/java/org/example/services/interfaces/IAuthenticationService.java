package org.example.services.interfaces;

import org.example.dto.AuthData;
import org.example.dto.RegisterDTO;

public interface IAuthenticationService {
    AuthData authenticate(String email, String password);

    AuthData register(RegisterDTO registerDTO);
}
