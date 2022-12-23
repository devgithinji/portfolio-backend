package com.densoft.portfolio.service.user;

import com.densoft.portfolio.dto.ProfileResponse;
import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.dto.UserUpdateDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.User;
import com.densoft.portfolio.repository.UserRepository;
import com.densoft.portfolio.utils.AWSS3Util;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ProfileResponse createProfile(UserCreateDTO userCreateDTO) throws IOException {
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
        return new ProfileResponse(userRepository.save(user),objectMapper);
    }

    @Override
    public ProfileResponse getProfile() throws JsonProcessingException {
        Integer userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "Id", String.valueOf(userId)));
        return new ProfileResponse(user, objectMapper);
    }

    @Override
    public User updateUser(UserUpdateDTO userUpdateDTO) throws IOException {
        //get auth user
        User user = new User();
        if (userUpdateDTO.getResume() != null && !userUpdateDTO.getResume().isEmpty()) {
            awss3Util.deleteFile(user.getResumePath());
            String filePath = awss3Util.uploadFile("profile", userUpdateDTO.getResume(), ObjectCannedACL.PRIVATE);
            user.setResumePath(filePath);
        }
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPhone(userUpdateDTO.getPhone());
        user.setSocialMediaLinks(objectMapper.writeValueAsString(userUpdateDTO.getSocialMediaLinks()));
        user.setPersonalStatement(user.getPersonalStatement());
        user.setSkills(objectMapper.writeValueAsString(userUpdateDTO.getSkills()));

        return userRepository.save(user);
    }
}
