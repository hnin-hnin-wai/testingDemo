package edu.miu.cse.ums.service;

import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> updateUser(String username,UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> getUserByName(String name);
    void deleteUser(String name);
    List<UserResponseDTO> getAllUsers();
}
