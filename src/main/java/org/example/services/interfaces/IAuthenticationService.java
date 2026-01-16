package org.example.services.interfaces;

import org.example.models.AuthData;

public interface IAuthenticationService {
    AuthData authenticate(String email, String password);
}
