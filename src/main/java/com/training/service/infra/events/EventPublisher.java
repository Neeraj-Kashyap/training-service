package com.training.service.infra.events;

import com.training.service.events.TrainingSessionCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * Publishes  events to Kafka.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, TrainingSessionCompletedEvent> kafkaTemplate;

    private static final String TOPIC = "training-events";

    /**
     * Publish a training session completed event.
     *
     * @param event the domain event
     * @throws org.springframework.kafka.KafkaException if publish fails
     */
    public void publishEvent(TrainingSessionCompletedEvent event) throws ExecutionException, InterruptedException {

        Message<TrainingSessionCompletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.KEY, event.getSessionId())  // Partition key
                .setHeader("eventId", event.getEventId())  // For tracking
                .setHeader("eventType", "TRAINING_SESSION_COMPLETED")
                .setHeader("source", event.getSource())
                .build();

        // Synchronous send within transaction context
        kafkaTemplate.send(message).get();

        log.debug("Event published to Kafka topic={}, sessionId={}, eventId={}",
                TOPIC, event.getSessionId(), event.getEventId());
    }
}