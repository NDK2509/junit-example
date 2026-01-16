package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.dto.RegisterDTO;

import java.util.Random;


@Data
@AllArgsConstructor
public class User { // table: users
    private Long id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = new Random().nextLong();
    }

    public static User fromRegisterDTO(RegisterDTO registerDTO) {
        return new User(registerDTO.getName(), registerDTO.getEmail(), registerDTO.getPassword());
    }
}
