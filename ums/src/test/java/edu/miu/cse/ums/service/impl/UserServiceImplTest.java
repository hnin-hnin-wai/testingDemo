package edu.miu.cse.ums.service.impl;

import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;
import edu.miu.cse.ums.model.User;
import edu.miu.cse.ums.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void createUser_validInput_returnsCreatedUser() {
        User savedUser=new User("AA","BB","AABB");
        Optional<UserResponseDTO> expectedUserResponseDTO=Optional.of(new UserResponseDTO("AABB"));// expected output
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        Optional<UserResponseDTO> userResponseDTO = userServiceImpl.createUser(new UserRequestDTO("AA","BB","AABB"));
        assert userResponseDTO.isPresent();
        assert expectedUserResponseDTO.get().equals(userResponseDTO.get());
    }


    @Test
    void createUserFails_whenUserAlreadyExists() {
        String username="AABB";
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO("AA", "BB", "AABB");
        User existingUser = new User("AA", "BB", "AABB");

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // Act
        Optional<UserResponseDTO> userResponseDTO = userServiceImpl.createUser(userRequestDTO);

        // Assert
        assertTrue(userResponseDTO.isEmpty());

    }

    //updateUser
    @Test
    void updateUser_validInput_returnsUpdatedUser() {
        String username="AABB";
        UserRequestDTO userRequestDTO = new UserRequestDTO("BB","CC","AABB");

        User existingUser=new User("AA","BB","AABB");
        User updatedUser=new User("BB","CC","AABB");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        Optional<UserResponseDTO> userResponseDTO=userServiceImpl.updateUser(username,userRequestDTO);
        assert userResponseDTO.isPresent();
    }

    //deleteUser
    @Test
    void deleteUser() {
        String username="AABB";
        User existingUser=new User("AA","BB","AABB");

       Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        userServiceImpl.deleteUser(username);

        Mockito.verify(userRepository, Mockito.times(1)).deleteByUsername(username);
    }


}