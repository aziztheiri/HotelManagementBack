package com.aziz.booksocialnetwork.services.auth;

import com.aziz.booksocialnetwork.dto.SignUpRequest;
import com.aziz.booksocialnetwork.dto.UserDto;
import com.aziz.booksocialnetwork.entities.User;
import com.aziz.booksocialnetwork.enums.UserRole;
import com.aziz.booksocialnetwork.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements  AuthService{
    private final UserRepository userRepository;
    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount =userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setVerified(true);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            log.info("Admin account created successfully !");
        }else{
            log.info("Admin account already exist!");
        }
    }
    public UserDto createUser(SignUpRequest signUpRequest){
        if(userRepository.findFirstByEmail(signUpRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User already exists"+signUpRequest.getEmail());
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setVerified(false);
        user.setImage(signUpRequest.getImage());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return  createdUser.getUserDto();
    }
}
