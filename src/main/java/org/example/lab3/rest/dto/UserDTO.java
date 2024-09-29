package org.example.lab3.rest.dto;

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

    @Schema(name = "Имя пользователя для идентификации", example = "Алексей Иванов")
    private String secretWord;

    @Schema(name = "Пароль пользователя", example = "secure_password")
    private String password;

    @Schema(name = "Наличие символом asd", example = "false")
    private boolean containsASD;

    @Schema(name = "Вектор", example = "[0.12, 0.45]")
    private double[] biometricVector;

    @Schema(name = "Вектор минимальных значений", example = "[0.12, 0.45]")
    private double[] minVi;

    @Schema(name = "Вектор максимальных значений", example = "[0.12, 0.45]")
    private double[] maxVi;

}
