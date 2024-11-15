package pl.akolata.testcontainers.infrastructure;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@Access(AccessType.FIELD)
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"publicId"})
@EqualsAndHashCode(of = {"publicId"})
public abstract class BaseEntity implements Serializable {

    @Column(name = "public_id", nullable = false, updatable = false, length = 13)
    private String publicId = TSID.Factory.getTsid().toString();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime modifiedAt;

    @Version
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "version", columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private Integer optLock;
}
