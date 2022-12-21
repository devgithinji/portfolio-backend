package com.densoft.portfolio.service.user;

import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.model.User;
import com.densoft.portfolio.repository.UserRepository;
import com.densoft.portfolio.utils.AWSS3Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final AWSS3Util awss3Util;

    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, AWSS3Util awss3Util, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.awss3Util = awss3Util;
        this.objectMapper = objectMapper;
    }

    @Override
    public User createProfile(UserCreateDTO userCreateDTO) throws IOException {
        String filePath = awss3Util.uploadFile("profile", userCreateDTO.getResume(), ObjectCannedACL.PRIVATE);
        User user = new User(
                userCreateDTO.getFirstName(),
                userCreateDTO.getLastName(),
                userCreateDTO.getEmail(),
                userCreateDTO.getPhone(),
                objectMapper.writeValueAsString(userCreateDTO.getSocialMediaLinks()),
                filePath,
                userCreateDTO.getPersonalStatement(),
                objectMapper.writeValueAsString(userCreateDTO.getSkills())
        );
        return userRepository.save(user);
    }

    @Override
    public User getProfile() {
        return null;
    }
}
