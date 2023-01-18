package com.densoft.portfolio.service.user;

import com.densoft.portfolio.dto.ProfileResponse;
import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.dto.UserUpdateDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.User;
import com.densoft.portfolio.repository.UserRepository;
import com.densoft.portfolio.utils.CloudinaryUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ProfileResponse createProfile(UserCreateDTO userCreateDTO) throws IOException {
        String filePath = CloudinaryUtil.UploadFile(userCreateDTO.getResume(), "profile", false);
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
        return new ProfileResponse(userRepository.save(user), objectMapper);
    }

    @Override
    public ProfileResponse getProfile() throws JsonProcessingException {
        Integer userId = 1;
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "Id", String.valueOf(userId)));
        return new ProfileResponse(user, objectMapper);
    }

    @Override
    public ProfileResponse updateUser(UserUpdateDTO userUpdateDTO) throws IOException {
        //get auth user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (userUpdateDTO.getResume() != null && !userUpdateDTO.getResume().isEmpty()) {
            if(user.getResumePath() != null){
                CloudinaryUtil.deleteFile(user.getResumePath(),false);
            }
            String filePath = CloudinaryUtil.UploadFile(userUpdateDTO.getResume(), "profile", false);
            user.setResumePath(filePath);
        }
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setEmail(userUpdateDTO.getEmail());
        user.setPhone(userUpdateDTO.getPhone());
        user.setSocialMediaLinks(objectMapper.writeValueAsString(userUpdateDTO.getSocialMediaLinks()));
        user.setPersonalStatement(userUpdateDTO.getPersonalStatement());
        user.setSkills(objectMapper.writeValueAsString(userUpdateDTO.getSkills()));

        User updatedUser = userRepository.save(user);

        return new ProfileResponse(updatedUser, objectMapper);
    }
}
