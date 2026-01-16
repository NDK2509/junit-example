package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.models.User;

@Data
@AllArgsConstructor
public class AuthData {
    User user; // DTO -> luoc bo bot nhung fields khong can thiet
    String accessToken;
}
