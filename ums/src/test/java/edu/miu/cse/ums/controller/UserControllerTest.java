package edu.miu.cse.ums.controller;

import com.google.gson.Gson;
import edu.miu.cse.ums.dto.request.UserRequestDTO;
import edu.miu.cse.ums.dto.response.UserResponseDTO;
import edu.miu.cse.ums.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)//Spring framework it loads the application context, but not fully
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean//Spring framework
    private UserService userService;

    @Test
    void createUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("AA", "BB", "AABB");
        UserResponseDTO userResponseDTO = new UserResponseDTO("AABB");
        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.of(userResponseDTO));


//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/v1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"AA\",\"lastName\":\"AA\",\"username\":\"AABB\"}")
//
//        ).andDo(MockMvcResultHandlers.print());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userRequestDTO)))
                        .andDo(MockMvcResultHandlers.print()).andExpectAll(
                                MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTO))
                        );

    }

    @Test
    void createUser_BadRequest() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("AA", "BB", "null");
        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.empty());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(userRequestDTO))
        ).andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.status().isBadRequest());
    }

        @Test
        void getUsers() throws Exception {
            List<UserResponseDTO> userResponseDTOs=new ArrayList<>();
            userResponseDTOs.add(new UserResponseDTO("AABB"));
            userResponseDTOs.add(new UserResponseDTO("CC"));
            userResponseDTOs.add(new UserResponseDTO("DD"));
            Mockito.when(userService.getAllUsers()).thenReturn((userResponseDTOs));
            mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/users"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpectAll(MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTOs)));
        }

        @Test
        void updateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("AA", "BB", "AABB");
        UserResponseDTO userResponseDTO = new UserResponseDTO("AABBUpdated");
        Mockito.when(userService.updateUser(userResponseDTO.username(), userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/users/"+userResponseDTO.username(), userResponseDTO.username())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(userRequestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTO)));
        }

        @Test
        void deleteUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/users/AABB"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent());

        }

}