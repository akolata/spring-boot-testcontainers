package pl.akolata.testcontainers.approval.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import pl.akolata.testcontainers.approval.domain.model.ApprovalStatus;
import pl.akolata.testcontainers.infrastructure.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "approval_history")
@Entity(name = "approval_history")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
class ApprovalHistoryEntity extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_history_seq_gen")
    @SequenceGenerator(name = "approval_history_seq_gen", sequenceName = "approval_history_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status", nullable = false, columnDefinition = "APPROVAL_STATUS")
    private ApprovalStatus status;

    @Column(name = "status_assigned_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime statusAssignedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApprovalEntity approval;
}
