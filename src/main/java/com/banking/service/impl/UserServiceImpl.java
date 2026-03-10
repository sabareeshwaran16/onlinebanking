package com.banking.service.impl;

import com.banking.model.dto.AccountDto;
import com.banking.model.dto.UserDto;
import com.banking.model.entity.AccountEntity;
import com.banking.model.entity.UserEntity;
import com.banking.repository.UserRepository;
import com.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();

        for (int i = 0; i < userEntities.size(); i++) {
            UserDto dto = new UserDto();
            UserEntity userEntity = userEntities.get(i);

            dto.setId(userEntity.getId());
            dto.setUsername(userEntity.getUsername());
            dto.setEmail(userEntity.getEmail());
            dto.setFullName(userEntity.getFullName());
            dto.setPhoneNumber(userEntity.getPhoneNumber());
            dto.setRole(userEntity.getRole());
            dto.setCreatedAt(userEntity.getCreatedAt());

            List<AccountEntity> accounts = userEntity.getAccountEntities();
            List<AccountDto> accountDtos = new ArrayList<>();

            if (accounts != null) {
                for (int j = 0; j < accounts.size(); j++) {
                    AccountDto accountDto = new AccountDto();
                    AccountEntity entity = accounts.get(j);
                    accountDto.setId(entity.getId());
                    accountDto.setAccountNumber(entity.getAccountNumber());
                    accountDto.setAccountType(entity.getAccountType());
                    accountDto.setBalance(entity.getBalance());
                    accountDto.setCreatedAt(entity.getCreatedAt());
                    accountDtos.add(accountDto);
                }
            }
            dto.setAccountDtos(accountDtos);

            userDtos.add(dto);
        }
        return userDtos;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFullName(userDto.getFullName());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());

        UserEntity savedUser = userRepository.save(userEntity);
        
        UserDto responseDto = new UserDto();
        responseDto.setId(savedUser.getId());
        responseDto.setUsername(savedUser.getUsername());
        responseDto.setEmail(savedUser.getEmail());
        responseDto.setFullName(savedUser.getFullName());
        
        return responseDto;
    }

    @Override
    public void updateUser(Long id, String fullName) {
        UserEntity entity = userRepository.findById(id).get();
        entity.setFullName(fullName);
        userRepository.save(entity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}