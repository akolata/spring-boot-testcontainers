package pl.akolata.testcontainers.approval.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.testcontainers.approval.domain.command.CreateApprovalCommand;
import pl.akolata.testcontainers.approval.domain.model.Approval;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApprovalsApplicationService {
    private final ApprovalRepository approvalRepository;
    private final ApprovalEntityMapper approvalEntityMapper;

    @Transactional
    public Approval createApproval(@NonNull CreateApprovalCommand command) {
        ApprovalEntity entity = approvalRepository.saveAndFlush(ApprovalEntity.fromCreateApprovalCommand(command));
        return approvalEntityMapper.toApproval(entity);
    }

    public Optional<Approval> getApprovalById(@NonNull String id) {
        return approvalRepository.findByPublicId(id).map(approvalEntityMapper::toApproval);
    }
}
