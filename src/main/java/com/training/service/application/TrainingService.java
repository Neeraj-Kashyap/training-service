package com.training.service.application;

import com.training.events.TrainingSessionCompletedEvent;
import com.training.service.domain.TrainingSession;
import com.training.service.infra.repository.TrainingSessionRepository;
import com.training.service.infra.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *Training Service class
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingSessionRepository sessionRepository;
    private final EventPublisher eventPublisher;

    /**
     * Complete a training session and publish the corresponding event.
     *
     * @param sessionId the training session ID
     */
    @Transactional
    public void completeTrainingSession(String sessionId) {

        // 1. Load the session (will throw if not found)
        TrainingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new Exceptions.SessionNotFoundException(
                        String.format("Training session %s not found", sessionId)
                ));

        // 2. Update status (domain logic)
        try {
            session.markComplete();
        } catch (IllegalStateException e) {
            throw new Exceptions.SessionAlreadyCompletedException(e.getMessage());
        }

        // 3. Persist updated session
        sessionRepository.save(session);

        // 4. Publish domain event (within same transaction)
        TrainingSessionCompletedEvent event = TrainingSessionCompletedEvent.of(
                session.getSessionId(),
                session.getTraineeId()
        );

        try {
            eventPublisher.publishEvent(event);
            log.info("Published TRAINING_SESSION_COMPLETED: sessionId={}, traineeId={}, eventId={}",
                    session.getSessionId(), session.getTraineeId(), event.getEventId());
        } catch (Exception e) {
            // If event publishing fails, the transaction will rollback.
            // Consider outbox pattern for production (separate write + async publish)
            log.error("Failed to publish event for sessionId={}", sessionId, e);
            throw new Exceptions.EventPublishingException("Failed to publish training completion event", e);
        }
    }

    /**
     * Retrieve session details (read-only).
     */
    @Transactional(readOnly = true)
    public TrainingSession getSession(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new Exceptions.SessionNotFoundException(
                        String.format("Training session %s not found", sessionId)
                ));
    }
}