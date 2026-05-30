package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.LoginResponse;
import com.example.examsystem.entity.SysUser;
import com.example.examsystem.repository.SysUserRepository;
import com.example.examsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final SysUserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, SysUserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BizException(4001, "invalid username or password");
        } catch (DisabledException e) {
            throw new BizException(4002, "user is disabled");
        }

        SysUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BizException(4001, "invalid username or password"));
        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole().name());
        return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
    }
}
