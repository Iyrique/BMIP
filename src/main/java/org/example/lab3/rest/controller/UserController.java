package org.example.lab3.rest.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.lab3.entity.User;
import org.example.lab3.mapper.UserMapper;
import org.example.lab3.rest.dto.UserDTO;
import org.example.lab3.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User-controller", description = "Контроллер пользователя")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Создание нового юзера")
    @PostMapping("/register")
    public User registerUser(@RequestParam @Parameter(description = "Имя") String name,
                             @RequestParam @Parameter(description = "Идентифицирующее слово") String secretWord,
                             @RequestParam @Parameter(description = "Пароль") String password,
                             @RequestParam @Parameter(description = "Вектор биометрических параметров") double[] biometricVector) {
        return userService.registerUser(name, secretWord, password, biometricVector);
    }

    @Operation(summary = "Регистрация пользователя", description = "Создание нового юзера")
    @PostMapping("/register-dto")
    @Hidden
    public User registerUser(@RequestBody @Parameter(description = "User") UserDTO userDTO) {
        return userService.registerUser(userDTO.getName(), userDTO.getSecretWord(), userDTO.getPassword(), userDTO.getBiometricVector());
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

//    @PostMapping("/authenticate")
//    @Hidden
//    public ResponseEntity<String> authenticateUser(@RequestParam @Parameter(description = "Имя") String name,
//                                                   @RequestParam @Parameter(description = "Пароль") String password,
//                                                   @RequestParam @Parameter(description = "Вектор биометрических параметров") double[] biometricVector) {
//        boolean isAuthenticated = userService.authenticateUser(name, password, biometricVector);
//
//        if (isAuthenticated) {
//            return ResponseEntity.ok("Authentication successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//        }
//    }
}
