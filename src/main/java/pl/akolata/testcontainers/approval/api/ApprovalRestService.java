package pl.akolata.testcontainers.approval.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.api.schema.CreateApprovalRequest;
import pl.akolata.testcontainers.approval.domain.ApprovalsApplicationService;
import pl.akolata.testcontainers.approval.domain.command.CreateApprovalCommand;
import pl.akolata.testcontainers.approval.domain.model.Approval;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ApprovalRestService {
    private final ApprovalMapperRest approvalMapper;
    private final ApprovalsApplicationService approvalsDomainService;

    public ApprovalDTO createApproval(CreateApprovalRequest request) {
        CreateApprovalCommand command = approvalMapper.toCreateApprovalCommand(request);
        Approval approval = approvalsDomainService.createApproval(command);
        return approvalMapper.toApprovalDTO(approval);
    }

    public Optional<ApprovalDTO> getApproval(String id) {
        return approvalsDomainService.getApprovalById(id).map(approvalMapper::toApprovalDTO);
    }
}
