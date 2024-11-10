package com.example.scrap_chef.service;


import com.example.scrap_chef.domain.user.User;
import com.example.scrap_chef.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encryptedPassword);

        // 데이터베이스에 저장
        return userRepository.save(user);
    }

}
