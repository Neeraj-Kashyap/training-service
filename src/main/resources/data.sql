

CREATE database training_db;

INSERT INTO training_db.training_sessions (session_id, trainee_id, status, version) VALUES
('1001', '2001', 'COMPLETED',1),
('1002', '2002', 'IN_PROGRESS',1),
('1003', '2003', 'IN_PROGRESS',1),
('1004', '2002', 'COMPLETED',2),
('1005', '2004', 'IN_PROGRESS',1);


SELECT * FROM training_db.training_sessions;
