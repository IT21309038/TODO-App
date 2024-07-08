package com.dms.todo.service;


import com.dms.todo.DTO.AuthResponseDTO;
import com.dms.todo.DTO.LoginDTO;
import com.dms.todo.DTO.RegisterDTO;
import com.dms.todo.entity.Role;
import com.dms.todo.entity.UserEntity;
import com.dms.todo.repo.RoleRepo;
import com.dms.todo.repo.UserRepo;
import com.dms.todo.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepo userRepo,
                       RoleRepo roleRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public void registerUser(RegisterDTO registerDTO) {
        if (userRepo.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        } else {
            UserEntity user = new UserEntity();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

            String type = registerDTO.getType().toUpperCase(); // Ensure type comparison is case-insensitive

            String roleName;
            switch (type) {
                case "ADMIN-USER":
                    roleName = "ADMIN";
                    break;
                case "GENERAL-USER":
                    roleName = "GENERAL";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type provided");
            }

            Optional<Role> userRole = roleRepo.findByName(roleName);
            if (userRole.isEmpty()) {
                throw new RuntimeException("Role not found for the provided user type");
            }

            user.setRoles(Collections.singletonList(userRole.get()));
            userRepo.save(user);
        }
    }

    public AuthResponseDTO loginUser(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);

            return new AuthResponseDTO(token);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password!");
        }
    }
}
