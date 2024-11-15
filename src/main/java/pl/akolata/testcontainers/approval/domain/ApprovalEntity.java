package pl.akolata.testcontainers.approval.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import pl.akolata.testcontainers.approval.domain.command.CreateApprovalCommand;
import pl.akolata.testcontainers.approval.domain.model.ApprovalStatus;
import pl.akolata.testcontainers.infrastructure.BaseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "approval")
@Entity(name = "approval")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "graph.Approval.history",
        attributeNodes = @NamedAttributeNode("history"))
class ApprovalEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_seq_gen")
    @SequenceGenerator(name = "approval_seq_gen", sequenceName = "approval_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status", nullable = false, columnDefinition = "APPROVAL_STATUS")
    private ApprovalStatus status = ApprovalStatus.CREATED;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(
            mappedBy = "approval",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<ApprovalHistoryEntity> history = new HashSet<>();

    static ApprovalEntity fromCreateApprovalCommand(@NonNull CreateApprovalCommand command) {
        ApprovalEntity entity = new ApprovalEntity();
        entity.description = command.getDescription();
        entity.status = ApprovalStatus.CREATED;

        ApprovalHistoryEntity approvalHistoryEntity = new ApprovalHistoryEntity();
        approvalHistoryEntity.setStatus(ApprovalStatus.CREATED);
        approvalHistoryEntity.setStatusAssignedAt(LocalDateTime.now());

        entity.history.add(approvalHistoryEntity);
        approvalHistoryEntity.setApproval(entity);

        return entity;
    }
}
