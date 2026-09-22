package com.training.service.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * TrainingSessionResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingSessionResponse {
    private String sessionId;
    private String traineeId;
    private String status;
    private Instant createdAt;
    private Instant completedAt;
    private String message;
}