package org.example.lab2.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Schema(name = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(name = "Имя пользователя", example = "john_doe")
    private String name;

    @Schema(name = "Пароль пользователя", example = "secure_password")
    private String password;

    @Schema(name = "Вектор", example = "[0.12, 0.45, 0.87, 0.67, 0.22]")
    private double[] biometricVector;

}
