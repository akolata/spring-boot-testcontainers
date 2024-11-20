package pl.akolata.testcontainers.approval.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ApprovalsControllerSpringBootTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createApproval_whenCreated_then201() throws Exception {
        ResultActions resultActions = mvc.perform(post("/api/v1/approvals")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "description": "A test approval description"
                        }
                        """)
        ).andDo(print());

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(header().string(HttpHeaders.LOCATION,
                Matchers.containsString("/api/v1/approvals")));

        resultActions.andExpect(jsonPath("$.id", notNullValue()));
        resultActions.andExpect(jsonPath("$.description", is("A test approval description")));
        resultActions.andExpect(jsonPath("$.status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.createdAt", notNullValue()));
        resultActions.andExpect(jsonPath("$.history[0].id", notNullValue()));
        resultActions.andExpect(jsonPath("$.history[0].status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.history[0].statusAssignedAt", notNullValue()));
    }

}
