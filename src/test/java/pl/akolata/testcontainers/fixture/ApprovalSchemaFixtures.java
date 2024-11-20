package pl.akolata.testcontainers.fixture;

import lombok.experimental.UtilityClass;
import pl.akolata.testcontainers.approval.api.schema.CreateApprovalRequest;

@UtilityClass
public class ApprovalSchemaFixtures {

    public static CreateApprovalRequest createApprovalRequest() {
        return CreateApprovalRequest.builder()
                .description("A test approval description")
                .build();
    }
}
