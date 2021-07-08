package com.JejuDojang;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {
	
	@Autowired
    MockMvc mockMvc;

    //@Test
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/styleSelect").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //@Test
    public void index_user() throws Exception {
        mockMvc.perform(get("/styleSelect").with(user("ㅁㅁ").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
