package com.densoft.portfolio.service.user;

import com.densoft.portfolio.dto.ProfileResponse;
import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.dto.UserUpdateDTO;
import com.densoft.portfolio.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface UserService {
    ProfileResponse createProfile(UserCreateDTO userCreateDTO) throws IOException;

    ProfileResponse getProfile() throws JsonProcessingException;

    User updateUser(UserUpdateDTO userUpdateDTO) throws IOException;
}
