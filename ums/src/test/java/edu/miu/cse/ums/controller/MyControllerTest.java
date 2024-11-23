package edu.miu.cse.ums.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = MyController.class)
class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;//test controller without actually starting the server

    @Test
    void home() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/home"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"));

//                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/home"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpectAll(MockMvcResultMatchers.status().isOk(),
//                MockMvcResultMatchers.content().string("Hello World"));



//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/home");
//        ResultActions resultActions=mockMvc.perform(requestBuilder);
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }
}