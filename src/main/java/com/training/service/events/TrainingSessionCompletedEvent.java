package com.training.service.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain event: Training session has been marked complete by the training service.
 *
 * Design notes:
 * - Include a unique eventId for tracking and idempotency
 * - Use Instant for UTC timestamps (serializes to ISO-8601)
 * - Include source service identification for debugging
 * - Version the schema early (v1 convention) for forward compatibility
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSessionCompletedEvent {

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("traineeId")
    private String traineeId;

    @JsonProperty("completedAt")
    private Instant completedAt;

    @JsonProperty("source")
    private String source;  // e.g., "training-service"

    @JsonProperty("schemaVersion")
    private String schemaVersion;  // e.g., "1.0"

    /**
     * Factory method with sensible defaults.
     */
    public static com.training.service.events.TrainingSessionCompletedEvent of(String sessionId, String traineeId) {
        return com.training.service.events.TrainingSessionCompletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .sessionId(sessionId)
                .traineeId(traineeId)
                .completedAt(Instant.now())
                .source("training-service")
                .schemaVersion("1.0")
                .build();
    }
}