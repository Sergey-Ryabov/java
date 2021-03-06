CREATE TABLE groups (
id BIGINT GENERATED BY DEFAULT AS IDENTITY, 
number INTEGER, 
facultyName VARCHAR(100), 
PRIMARY KEY (id))

CREATE TABLE student (
id BIGINT GENERATED BY DEFAULT AS IDENTITY, 
name VARCHAR(50),
surname VARCHAR(50),
patronymic VARCHAR(50),
birthday DATE, 
groupId BIGINT, 
PRIMARY KEY (id),
CONSTRAINT FKGROUP FOREIGN KEY (groupId) REFERENCES groups(id))
