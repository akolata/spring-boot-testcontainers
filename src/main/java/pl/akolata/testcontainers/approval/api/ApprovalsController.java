package pl.akolata.testcontainers.approval.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.akolata.testcontainers.approval.api.schema.ApprovalDTO;
import pl.akolata.testcontainers.approval.api.schema.CreateApprovalRequest;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/approvals")
class ApprovalsController {
    private final ApprovalRestService approvalsService;

    @PostMapping
    public ResponseEntity<ApprovalDTO> createApproval(@RequestBody @Valid CreateApprovalRequest request) {
        ApprovalDTO approval = approvalsService.createApproval(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(approval.getId())
                .toUri();
        return ResponseEntity.created(uri).body(approval);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalDTO> getApproval(@PathVariable("id") String id) {
        return approvalsService.getApproval(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
