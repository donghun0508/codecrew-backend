package site.codecrew.r2dbc.jpa;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

@Getter
public abstract class BaseEntity {

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;

    @Column("status")
    private EntityStatus status = EntityStatus.ACTIVE;

    protected void guard() {}

    public void delete() {
        if (this.deletedAt == null) {
            this.deletedAt = LocalDateTime.now();
        }
    }

    public void restore() {
        if (this.deletedAt != null) {
            this.deletedAt = null;
        }
    }
}