package pl.akolata.testcontainers.approval.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.domain.ApprovalsApplicationService;
import pl.akolata.testcontainers.approval.domain.model.Approval;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ApprovalsControllerTestcontainersTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.0"))
            .withDatabaseName("testcontainers")
            .withDatabaseName("postgres")
            .withPassword("postgres");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApprovalsApplicationService approvalsApplicationService;

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
        resultActions.andExpect(header().string(HttpHeaders.LOCATION, containsString("/api/v1/approvals")));

        resultActions.andExpect(jsonPath("$.id", notNullValue()));
        resultActions.andExpect(jsonPath("$.description", is("A test approval description")));
        resultActions.andExpect(jsonPath("$.status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.createdAt", notNullValue()));
        resultActions.andExpect(jsonPath("$.history[0].id", notNullValue()));
        resultActions.andExpect(jsonPath("$.history[0].status", is("CREATED")));
        resultActions.andExpect(jsonPath("$.history[0].statusAssignedAt", notNullValue()));

        String responseJson = resultActions.andReturn().getResponse().getContentAsString();
        ApprovalDTO approvalDTO = objectMapper.readValue(responseJson, ApprovalDTO.class);

        Optional<Approval> approvalOptional = approvalsApplicationService.getApprovalById(approvalDTO.getId());
        assertTrue(approvalOptional.isPresent());

        Approval approval = approvalOptional.get();

        log.info("{}", approval);
    }

}
