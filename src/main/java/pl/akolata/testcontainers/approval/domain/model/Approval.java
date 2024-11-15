package pl.akolata.testcontainers.approval.domain.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Approval {
    String id;
    String description;
    ApprovalStatus status;
    LocalDateTime createdAt;
    @Builder.Default
    List<HistoryEntry> history = new ArrayList<>();

    @Value
    @Builder
    public static class HistoryEntry {
        String id;
        ApprovalStatus status;
        LocalDateTime statusAssignedAt;
    }
}
