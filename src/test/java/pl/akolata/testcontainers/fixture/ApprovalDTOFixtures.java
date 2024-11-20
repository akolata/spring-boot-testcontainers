package pl.akolata.testcontainers.fixture;

import io.hypersistence.tsid.TSID;
import lombok.experimental.UtilityClass;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.api.schema.ApprovalStatus;

import java.time.OffsetDateTime;
import java.util.List;

@UtilityClass
public class ApprovalDTOFixtures {

    public static ApprovalDTO createdApprovalDTO() {
        OffsetDateTime createdAt = OffsetDateTime.now(ClockFixture.FIXED_CLOCK);
        return ApprovalDTO.builder()
                .id(TSID.Factory.getTsid().toString())
                .description("A test description")
                .status(ApprovalStatus.CREATED)
                .createdAt(createdAt)
                .history(List.of(
                        ApprovalDTO.HistoryEntry.builder()
                                .id(TSID.Factory.getTsid().toString())
                                .status(ApprovalStatus.CREATED)
                                .statusAssignedAt(createdAt)
                                .build()
                ))
                .build();
    }
}
