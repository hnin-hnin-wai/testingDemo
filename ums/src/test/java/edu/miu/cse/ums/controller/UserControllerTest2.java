package edu.miu.cse.ums.controller;

import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;
import edu.miu.cse.ums.model.User;
import edu.miu.cse.ums.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

//Spring application context will not be loaded
@ExtendWith(MockitoExtension.class)
class UserControllerTest2 {

    @Mock //Mock UserService and inject it into UserContoller
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void createUser_validInput_returnsCreatedUser() {
        UserRequestDTO  userRequestDTO=new UserRequestDTO("AA","BB","AABB");
        //User savedUser=new User("AA","BB","AABB");
        UserResponseDTO userResponseDTO=new UserResponseDTO("AABB");

        Mockito.when(userService.createUser(Mockito.any(UserRequestDTO.class))).thenReturn(Optional.of(userResponseDTO));
        ResponseEntity<UserResponseDTO> responseEntity=userController.createUser(userRequestDTO);
        assert responseEntity.getStatusCode()== HttpStatus.CREATED;
        Assertions.assertEquals(responseEntity.getBody(),userResponseDTO);
    }

    @Test
    public void updateUser_invalidUsername_noUpdate(){
        UserRequestDTO  userRequestDTO=new UserRequestDTO("AA","BB","AABB");
        String nonExistingUsername="jk";
        Optional<UserResponseDTO> expectedUserResponseDTO=Optional.empty();
        //Set up the mock behaviour
        Mockito.when(userService.updateUser(nonExistingUsername,userRequestDTO)).thenReturn(expectedUserResponseDTO);

        ResponseEntity<UserResponseDTO> responseEntity=userController.updateUser(nonExistingUsername,userRequestDTO);
        System.out.println(responseEntity.getStatusCode());
        assert responseEntity.getStatusCode()== HttpStatus.NOT_FOUND;
        assert responseEntity.getBody()==null;
    }


    @Test
    public void updateUser_validInput_updatesUser() {
        String username = "bbb";
        UserRequestDTO userRequestDTO = new UserRequestDTO("mathew", "kurian", "mk");
        UserResponseDTO userResponseDTO = new UserResponseDTO("mk");
        Mockito.when(userService.updateUser(username, userRequestDTO)).thenReturn(Optional.of(userResponseDTO));
        ResponseEntity<UserResponseDTO> responseEntity = userController.updateUser(username, userRequestDTO);
        System.out.println("Before:::" + responseEntity.getBody());
        System.out.println(responseEntity.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(userResponseDTO, responseEntity.getBody());
        System.out.println("After ::::" + responseEntity.getBody());
    }


}