package com.training.service.domain;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Training session aggregate root.
 * */

@Entity
@Table(name = "training_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSession {

    @Id
    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "trainee_id", nullable = false)
    private String traineeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TrainingStatus status;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)"
    )
    private Instant createdAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
        if (this.status == null) {
            this.status = TrainingStatus.CREATED;
        }
    }

    /**
     * Mark this session as complete.
     * Throws exception if already completed (idempotency guard).
     */
    public void markComplete() {
        if (this.status == TrainingStatus.COMPLETED) {
            throw new IllegalStateException(
                    String.format("Session %s is already completed", sessionId)
            );
        }
        this.status = TrainingStatus.COMPLETED;
        this.completedAt = Instant.now();
    }

    public enum TrainingStatus {
        CREATED, IN_PROGRESS, COMPLETED
    }
}