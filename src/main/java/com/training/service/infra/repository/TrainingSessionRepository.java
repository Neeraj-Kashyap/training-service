package com.training.service.infra.repository;

import com.training.service.domain.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, String> {
}