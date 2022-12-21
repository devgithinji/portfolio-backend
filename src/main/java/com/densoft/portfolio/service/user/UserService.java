package com.densoft.portfolio.service.user;

import com.densoft.portfolio.dto.UserCreateDTO;
import com.densoft.portfolio.model.User;

import java.io.IOException;

public interface UserService {
    User createProfile(UserCreateDTO userCreateDTO) throws IOException;

    User getProfile();
}
