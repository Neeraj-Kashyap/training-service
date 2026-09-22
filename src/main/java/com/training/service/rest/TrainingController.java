package com.training.service.rest;

import com.training.service.application.Exceptions.*;
import com.training.service.application.TrainingService;
import com.training.service.domain.TrainingSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API for training session operations.
 */
@RestController
@RequestMapping("/training-sessions")
@Slf4j
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    /**
     * Post mapping for the session
     * @param sessionId : session ID
     * @return: Response Entity details
     */
    @PostMapping("/{sessionId}/complete")
    public ResponseEntity<TrainingSessionResponse> completeTrainingSession(
            @PathVariable String sessionId) {

        log.info("Request to complete training session: {}", sessionId);

        try {
            trainingService.completeTrainingSession(sessionId);

            // Fetch updated session for response
            TrainingSession session = trainingService.getSession(sessionId);

            TrainingSessionResponse response = TrainingSessionResponse.builder()
                    .sessionId(session.getSessionId())
                    .traineeId(session.getTraineeId())
                    .status(session.getStatus().toString())
                    .completedAt(session.getCompletedAt())
                    .message("Training session completed successfully")
                    .build();

            log.info("Training session {} completed by trainee {}", sessionId, session.getTraineeId());
            return ResponseEntity.ok(response);

        } catch (SessionNotFoundException e) {
            log.warn("Session not found: {}", sessionId);
            return ResponseEntity.notFound().build();

        } catch (SessionAlreadyCompletedException e) {
            log.warn("Session already completed: {}", sessionId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(TrainingSessionResponse.builder()
                            .sessionId(sessionId)
                            .message("Session is already completed")
                            .build());

        } catch (EventPublishingException e) {
            log.error("Failed to publish event for session {}", sessionId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(TrainingSessionResponse.builder()
                            .sessionId(sessionId)
                            .message("Failed to process training completion")
                            .build());
        }
    }

    /**
     * Checking whether service is up or not
     * @return: Service is up
     */
    @GetMapping("/checkup")
    public String checkEndpoint(){
        return "service is up";
    }

}