package com.debitnotification.springserver.integrationtests.user;

import com.debitnotification.springserver.UserRole;
import com.debitnotification.springserver.configuration.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class TestUserController {

    private static User user;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setUserName("testuser");
        user.setPassword("Thinkpositive");
        user.setRole(UserRole.NORMAL);
        user.setEmail("testuser@gmail.com");

    }

    @Test
    void testCreateUser() throws Exception {

        String userDataAsString = om.writeValueAsString(user);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/create")
                                .content(userDataAsString)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        User resultUser = om.readValue(contentAsString, User.class);
        Assertions.assertEquals(user.getUserName(), resultUser.getUserName());

    }

    @Test
    void testUserRequestWithoutUserName() throws Exception {
        user.setUserName(null);
        String userDataAsString = om.writeValueAsString(user);

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/user/create")
                                .content(userDataAsString)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());


    }


}