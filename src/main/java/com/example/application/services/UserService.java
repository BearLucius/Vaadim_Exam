package com.example.application.services;

import com.example.application.Repository.UserRepository;
import com.example.application.data.UserEntity;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final String CURRENT_USER_SESSION_KEY = "currentUser";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getUserByUsername(String username) {
        // Check for the admin user first
        if ("admin".equals(username)) {
            return new UserEntity("admin", "admin", "ADMIN");
        }
        return userRepository.findByUsername(username).orElse(null);
    }

    public void setCurrentUser(UserEntity userEntity) {
        VaadinSession.getCurrent().setAttribute(CURRENT_USER_SESSION_KEY, userEntity);
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) VaadinSession.getCurrent().getAttribute(CURRENT_USER_SESSION_KEY);
    }

    public void logout() {
        VaadinSession.getCurrent().setAttribute(CURRENT_USER_SESSION_KEY, null);
    }





}