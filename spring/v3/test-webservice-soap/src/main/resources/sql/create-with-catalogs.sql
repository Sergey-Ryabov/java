use TEST;


CREATE TABLE applicant (
	applicantId BIGINT IdENTITY(1,1) PRIMARY KEY,
	lastName VARCHAR(100),
	firstName VARCHAR(100),
	middleName VARCHAR(100),
	personINN VARCHAR(100),
	personEMail VARCHAR(100)
)

CREATE TABLE objectType (
	objectTypeId BIGINT IdENTITY(1,1) PRIMARY KEY,
	objectTypeName VARCHAR(250)
)

CREATE TABLE objects(
	objectId BIGINT IdENTITY(1,1) PRIMARY KEY,
	nameObject VARCHAR(4000),
	power INT,
	applicantId VARCHAR(50) FOREIGN KEY REFERENCES applicant(applicantId) ON DELETE CASCADE,
	objectTypeId BIGINT FOREIGN KEY REFERENCES objectType(objectTypeId)
)

CREATE TABLE docs (
	docsId BIGINT IdENTITY(1,1) PRIMARY KEY,
	requiredInfo  VARCHAR(250),
	objectId BIGINT NOT NULL UNIQUE FOREIGN KEY REFERENCES objects(objectId) ON DELETE CASCADE
)

CREATE TABLE docsOptional (
	docsOptionalId BIGINT IdENTITY(1,1) PRIMARY KEY,
	namePart VARCHAR(250),
	shifr VARCHAR(250),
	objectId BIGINT NOT NULL FOREIGN KEY REFERENCES objects(objectId) ON DELETE CASCADE
)





