package pl.akolata.testcontainers.approval.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    @EntityGraph(value = "graph.Approval.history")
    Optional<ApprovalEntity> findByPublicId(String publicId);
}
