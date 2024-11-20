package pl.akolata.testcontainers.approval.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.api.schema.CreateApprovalRequest;
import pl.akolata.testcontainers.fixture.ApprovalDTOFixtures;
import pl.akolata.testcontainers.fixture.ApprovalSchemaFixtures;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ApprovalsController.class)
class ApprovalsControllerWebMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private ApprovalRestService approvalRestService;

    @Test
    void createApproval_whenCreated_then201() throws Exception {
        CreateApprovalRequest expectedRequest = ApprovalSchemaFixtures.createApprovalRequest();
        ApprovalDTO expectedApprovalDTO = ApprovalDTOFixtures.createdApprovalDTO();
        when(approvalRestService.createApproval(eq(expectedRequest))).thenReturn(expectedApprovalDTO);

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
                endsWith(String.format("/api/v1/approvals/%s", expectedApprovalDTO.getId()))));

        String response = resultActions.andReturn().getResponse().getContentAsString();
        ApprovalDTO actualResponse = om.readValue(response, ApprovalDTO.class);

        assertEquals(expectedApprovalDTO, actualResponse);

        resultActions.andExpect(jsonPath("$.id", notNullValue()));
        resultActions.andExpect(jsonPath("$.description", is("A test description")));
        resultActions.andExpect(jsonPath("$.status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.createdAt", is("2024-11-19T12:00:00.000Z")));
        resultActions.andExpect(jsonPath("$.history[0].id", notNullValue()));
        resultActions.andExpect(jsonPath("$.history[0].status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.history[0].statusAssignedAt", is("2024-11-19T12:00:00.000Z")));
    }
}