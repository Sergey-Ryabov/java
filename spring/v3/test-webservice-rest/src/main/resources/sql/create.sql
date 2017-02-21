CREATE DATABASE test_webservice_rest;

CREATE TABLE temperature (
	temperatureId BIGINT AUTO_INCREMENT PRIMARY KEY,
	tempValue DOUBLE NOT NULL,
	tempDate TIMESTAMP NOT NULL
);