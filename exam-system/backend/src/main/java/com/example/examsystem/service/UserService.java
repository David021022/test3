package com.example.examsystem.service;

import com.example.examsystem.common.BizException;
import com.example.examsystem.dto.UserCreateRequest;
import com.example.examsystem.dto.UserUpdateRequest;
import com.example.examsystem.dto.UserView;
import com.example.examsystem.entity.SysUser;
import com.example.examsystem.repository.SysUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final SysUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(SysUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserView> list() {
        return userRepository.findAll().stream()
                .map(u -> new UserView(u.getId(), u.getUsername(), u.getRole(), u.getStatus()))
                .collect(Collectors.toList());
    }

    public UserView create(UserCreateRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BizException(4003, "username already exists");
        }
        SysUser user = new SysUser();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        user.setStatus(req.getStatus());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new UserView(user.getId(), user.getUsername(), user.getRole(), user.getStatus());
    }

    public UserView update(Long id, UserUpdateRequest req) {
        SysUser user = userRepository.findById(id).orElseThrow(() -> new BizException(404, "user not found"));
        user.setRole(req.getRole());
        user.setStatus(req.getStatus());
        userRepository.save(user);
        return new UserView(user.getId(), user.getUsername(), user.getRole(), user.getStatus());
    }

    public void resetPassword(Long id, String newPassword) {
        SysUser user = userRepository.findById(id).orElseThrow(() -> new BizException(404, "user not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
