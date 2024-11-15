package pl.akolata.testcontainers.approval.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.akolata.testcontainers.approval.domain.model.Approval;

@Mapper(componentModel = "spring")
interface ApprovalEntityMapper {

    @Mapping(target = "id", source = "publicId")
    Approval toApproval(ApprovalEntity approvalEntity);

    @Mapping(target = "id", source = "publicId")
    Approval.HistoryEntry toApprovalHistoryEntry(ApprovalHistoryEntity entity);
}
