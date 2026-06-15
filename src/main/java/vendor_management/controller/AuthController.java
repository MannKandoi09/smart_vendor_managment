package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vendor_management.dto.AuthResponse;
import vendor_management.dto.LoginRequest;
import vendor_management.dto.RegisterRequest;
import vendor_management.entity.User;
import vendor_management.security.JwtUtil;
import vendor_management.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request) {

        String username =
                userService.login(
                        request.getUsername(),
                        request.getPassword());

        String token =
                jwtUtil.generateToken(username);

        return new AuthResponse(token);
    }
}