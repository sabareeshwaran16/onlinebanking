package com.banking.service;

import com.banking.model.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto createUser(UserDto userDto);
    void updateUser(Long id, String fullName);
    void deleteUser(Long id);
}