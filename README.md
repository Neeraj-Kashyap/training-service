# training-service
Training Service Module for a trainee who completes a training session


The Training Service contains 2 endpoints in the controller.


1) /training-sessions/checkup
   For checking whether the service is up or not.
2) /training-sessions/{sessionId}/complete
    The above endpoint will check the training session

Technologies or Stack Used:
1) Java 17
2) Spring Boot
3) Apache Kafka
4) MySQL
5) Lombok Plugin

PREREQUISITES:

1) CREATE DATABASE training_db. (Refer data.sql file) 
2) Clone the training-service repo and make it running.
3) INSERT few record in the DB. (Refer data.sql file)


Use the given repo url to clone

url: 

1) CREATE DATABASE progress_db. (Refer data.sql)
2) Clone the progress-service and make it running


Now execute the /complete endpoint with the session id. 
Check the progress-service, it will get the kafka events.


Refer collection mentioned in the resources' folder.
