package edu.miu.cse.ums.controller;

import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;
import edu.miu.cse.ums.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        Optional<UserResponseDTO> userResponseDTO=userService.createUser(userRequestDTO);
        if(userResponseDTO.isPresent()){
            return new ResponseEntity<>(userResponseDTO.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> userResponseDTOs=userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOs);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String username, @RequestBody UserRequestDTO userRequestDTO){
        Optional<UserResponseDTO> userResponseDTO=userService.updateUser(username, userRequestDTO);
        if(userResponseDTO.isPresent()){
            return new ResponseEntity<>(userResponseDTO.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
