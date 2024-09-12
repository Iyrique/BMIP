package org.example.lab2.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.lab2.entity.User;
import org.example.lab2.mapper.UserMapper;
import org.example.lab2.rest.dto.UserDTO;
import org.example.lab2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User-controller", description = "Контроллер пользователя")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Создание нового юзера")
    @PostMapping("/register-all")
    public User registerUser(@RequestParam @Parameter(description = "Имя") String name,
                             @RequestParam @Parameter(description = "Пароль") String password,
                             @RequestParam @Parameter(description = "Вектор биометрических параметров") double[] biometricVector) {
        return userService.registerUser(name, password, biometricVector);
    }

    @Operation(summary = "Регистрация пользователя", description = "Создание нового юзера")
    @PostMapping("/register-dto")
    public User registerUser(@RequestBody @Parameter(description = "User") UserDTO userDTO) {
        return userService.registerUser(userDTO.getName(), userDTO.getPassword(), userDTO.getBiometricVector());
    }

    @Operation(summary = "Получение всех юзеров")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Получение всех юзеров с asd в пароле")
    @GetMapping("/with-asd")
    public List<UserDTO> getUsersWithASDInPassword() {
        return userService.getUsersWithASDInPassword();
    }

    @Operation(summary = "Получение юзера по id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable @Parameter(description = "UserID") Long id) {
        return userService.getUser(id);
    }
}
