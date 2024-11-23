package edu.miu.cse.ums.service.impl;

import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;
import edu.miu.cse.ums.model.User;
import edu.miu.cse.ums.repository.UserRepository;
import edu.miu.cse.ums.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        if(userRepository.findByUsername(userRequestDTO.username()).isPresent()){
            return Optional.empty();
        }
        User user = new User(userRequestDTO.firstName(),userRequestDTO.lastName(),userRequestDTO.username());
        User savedUser=userRepository.save(user);
        UserResponseDTO userResponseDTO=new UserResponseDTO(savedUser.getUsername());
        return Optional.of(userResponseDTO);
    }

    @Override
    public Optional<UserResponseDTO> updateUser(String username, UserRequestDTO userRequestDTO) {
       Optional<User> userFound=userRepository.findByUsername(username);
           if(userFound.isPresent()){
               User user=userFound.get();
               user.setFirstName(userRequestDTO.firstName());
               user.setLastName(userRequestDTO.lastName());
               user.setUsername(userRequestDTO.username());
               User savedUser=userRepository.save(user);
               UserResponseDTO userResponseDTO=new UserResponseDTO(savedUser.getUsername());
               return Optional.of(userResponseDTO);
           }

        return Optional.empty();
    }


    @Override
    public Optional<UserResponseDTO> getUserByName(String name) {
        Optional<User> userFound=userRepository.findByUsername(name);
        if(userFound.isPresent()){
            UserResponseDTO userResponseDTO=new UserResponseDTO(userFound.get().getUsername());
            return Optional.of(userResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteUser(String name) {

        getUserByName(name).ifPresent(userResponseDTO->{
            userRepository.deleteByUsername(userResponseDTO.username());
        });

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOs=new ArrayList<>();
        for(User user:users){
            UserResponseDTO userResponseDTO=new UserResponseDTO(user.getUsername());
            userResponseDTOs.add(userResponseDTO);
        }
        return userResponseDTOs;
    }
}
