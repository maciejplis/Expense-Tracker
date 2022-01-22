package matthias.expense_tracker.common;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity extends BaseEntity {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private Instant modifiedAt;
}
