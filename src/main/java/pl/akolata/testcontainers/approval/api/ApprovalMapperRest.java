package pl.akolata.testcontainers.approval.api;

import org.mapstruct.Mapper;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.api.schema.CreateApprovalRequest;
import pl.akolata.testcontainers.approval.domain.command.CreateApprovalCommand;
import pl.akolata.testcontainers.approval.domain.model.Approval;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
interface ApprovalMapperRest {

    CreateApprovalCommand toCreateApprovalCommand(CreateApprovalRequest request);

    ApprovalDTO toApprovalDTO(Approval approval);

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
        return zonedDateTime.toOffsetDateTime();
    }
}
