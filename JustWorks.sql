CREATE OR REPLACE DATABASE justworks;

USE justworks;

CREATE OR REPLACE TABLE user(
	dni VARCHAR(9),
    	name VARCHAR(15),
    	surname VARCHAR(30),
    	eMail VARCHAR(50),
    	user VARCHAR(15),
    	password VARCHAR(128),
    	CONSTRAINT usuario_pk PRIMARY KEY (dni),
	CONSTRAINT usuario_uk UNIQUE KEY (user)
);

CREATE OR REPLACE TABLE worker(
    	id INT AUTO_INCREMENT,
	dni VARCHAR(9),
    	CONSTRAINT worker_pk PRIMARY KEY (id),
    	CONSTRAINT worker_uk UNIQUE KEY (dni),
    	CONSTRAINT worker_user_fk FOREIGN KEY (dni) REFERENCES user(dni)
);

CREATE OR REPLACE TABLE businessman(
	id INT AUTO_INCREMENT,
	dni VARCHAR(9),
    	CONSTRAINT businessman_pk PRIMARY KEY (id),
    	CONSTRAINT businessman_uk UNIQUE KEY (dni),
    	CONSTRAINT businessman_user_fk FOREIGN KEY (dni) REFERENCES user(dni)
);

CREATE OR REPLACE TABLE alert(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	workerId INT,
    	CONSTRAINT alert_pk PRIMARY KEY (id),
    	CONSTRAINT alert_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id)
);

CREATE OR REPLACE TABLE label(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	CONSTRAINT label_pk PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE label_alert(
    	id INT AUTO_INCREMENT,
	labelId INT,
    	alertId INT,
    	CONSTRAINT labelAlert_pk PRIMARY KEY (id),
    	CONSTRAINT labelAlert_uk UNIQUE  KEY (labelId,alertId),
    	CONSTRAINT labelAlert_label_fk FOREIGN KEY (labelId) REFERENCES label(id),
    	CONSTRAINT labelAlert_alert_fk FOREIGN KEY (alertId) REFERENCES alert(id)
);	


CREATE OR REPLACE TABLE ofert(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	description VARCHAR(30),
   	ubication VARCHAR(25),
    	salary INT,
    	contractType VARCHAR(10),
    	businessmanId INT,
    	CONSTRAINT ofert_pk PRIMARY KEY (id),
    	CONSTRAINT ofert_businessman_fk FOREIGN KEY (businessmanId) REFERENCES businessman(id)
);

CREATE OR REPLACE TABLE label_ofert(
	id INT AUTO_INCREMENT,
    	labelId INT,
    	ofertId INT,
    	CONSTRAINT labelOfert_pk PRIMARY KEY (id),
	CONSTRAINT labelOfert_uk UNIQUE KEY (labelId,ofertId),
    	CONSTRAINT labelOfert_label_fk FOREIGN KEY (labelId) REFERENCES label(id),
    	CONSTRAINT labelOfert_ofert_fk FOREIGN KEY (ofertId) REFERENCES ofert(id)
);



CREATE OR REPLACE TABLE skill(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	description VARCHAR(30),
    	workerId INT,
    	CONSTRAINT skill_pk PRIMARY KEY (id),
    	CONSTRAINT skill_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id)
);

CREATE OR REPLACE TABLE knowledge(
	id INT AUTO_INCREMENT,
   	name VARCHAR(15),
   	fechaInicio DATE,
   	fechaFin DATE,
    	workerId INT,
    	CONSTRAINT knowledge_pk PRIMARY KEY (id),
    	CONSTRAINT knowledge_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id)
);

CREATE OR REPLACE TABLE skill_knowledge(
    	id INT AUTO_INCREMENT,
	skillId INT,
    	knowledgeId INT,
    	CONSTRAINT skillKnowledge_pk PRIMARY KEY (id),
    	CONSTRAINT skillKnowledge_uk UNIQUE KEY (skillId,knowledgeId),
	CONSTRAINT skillKnowledge_skill_fk FOREIGN KEY (skillId) REFERENCES skill(id),
    	CONSTRAINT skillKnowledge_knowledge_fk FOREIGN KEY (knowledgeId) REFERENCES knowledge(id)
);


CREATE OR REPLACE TABLE workExperience(
    	id INT,
    	workName VARCHAR(20),
    	company VARCHAR(15),
    	CONSTRAINT workExperience_pk PRIMARY KEY (id),
    	CONSTRAINT workExperience_knowledge_fk FOREIGN KEY (id) REFERENCES knowledge(id)
);

CREATE OR REPLACE TABLE qualification(
    	id INT,
    	qualificationName VARCHAR(20),
    	studyCentre VARCHAR(15),
    	CONSTRAINT qualification_pk PRIMARY KEY (id),
    	CONSTRAINT qualification_knowledge_fk FOREIGN KEY (id) REFERENCES knowledge(id)
);

CREATE OR REPLACE TABLE curriculum(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	description VARCHAR(30),
    	workerId INT,
    	CONSTRAINT curriculum_pk PRIMARY KEY (id),
    	CONSTRAINT curriculum_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id)
);

CREATE OR REPLACE TABLE skill_curriculum(
    	id INT AUTO_INCREMENT,
	skillId INT,
    	curriculumID INT,
    	CONSTRAINT skillCurriculum_pk PRIMARY KEY (id),	
    	CONSTRAINT skillCurriculum_uk UNIQUE KEY (skillId,curriculumID),
	CONSTRAINT skillCurriculum_skill_fk FOREIGN KEY (skillId) REFERENCES skill(id),
    	CONSTRAINT skillCurriculum_curriculum_fk FOREIGN KEY (curriculumID) REFERENCES curriculum(id)	
);	


CREATE OR REPLACE TABLE folder(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	workerId INT,
	parentFolderId INT NULL,
	CONSTRAINT folder_pk PRIMARY KEY(id),
	CONSTRAINT folder_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id),
   	CONSTRAINT folder_folder_fk FOREIGN KEY (parentFolderId) REFERENCES folder(id)
);

CREATE OR REPLACE TABLE curriculum_folder(
    	id INT AUTO_INCREMENT,
	curriculumId INT,
    	folderId INT,
    	CONSTRAINT curriculumFolder_pk PRIMARY KEY (id),
    	CONSTRAINT curriculumFolder_uk UNIQUE KEY (curriculumId,folderId),
    	CONSTRAINT curriculumFolder_curriculum_fk FOREIGN KEY (curriculumId) REFERENCES curriculum(id),
	CONSTRAINT curriculumFolder_folder_fk FOREIGN KEY (folderId) REFERENCES folder(Id)
);

CREATE OR REPLACE TABLE candidature(
	id INT AUTO_INCREMENT,
    	realizationDate date,
    	ofertID INT,
    	curriculumId INT,
    	CONSTRAINT candidature_pk PRIMARY KEY (id),
    	CONSTRAINT candidature_ofert_fk FOREIGN KEY (ofertId) REFERENCES ofert(id),
    	CONSTRAINT candidature_curriculum_fk FOREIGN KEY (curriculumId) REFERENCES curriculum(id)
);


CREATE OR REPLACE TABLE message(
	id INT AUTO_INCREMENT,
    	content VARCHAR(100),
    	sender BOOLEAN,
   	sendDate DATE,
	candidatureID INT,
    	CONSTRAINT message_pk PRIMARY KEY(id),
    	CONSTRAINT message_candidatura_fk FOREIGN KEY (candidatureId) REFERENCES candidature(id)
);

INSERT INTO `user`(`dni`, `name`, `surname`, `eMail`, `user`, `password`) 
	VALUES ('44058714Y','David','Ramos Ponce','davidramosconil14@gmail.com','david','david'),
		 ('12345678A','Businessman1','acab','businessman1@gmail.com','businessman1','5e0d6ab3e6fe9fd0abef09d07ebed744183ee943b86703aa5b4cb28a775c9b917867f7b4c7c6bd5b51fff27fa742c86ffc98445c6b12e0934041bad28bb072cd'),
		 ('87654321A','Businessman2','acab','businessman2@gmail.com','businessman2','33a87c98da09a6d151bda75317c322dc9d3496f3bdef5bcaa7c2109e5b3be402a0e3c315cb635806ceeeecd1d10d5e422b19deb632d6a4d93dc1813831d62541'),
		 ('78945612W','Worker1','acab','worker1@gmail.com','worker1','a85606d4cd9384b2fb5d7e93479de1dff034b9100907b3078a129faff3912c3a390b2dc54782bafe1cfa90cce29a9b44811a1d63da212b96dfee65f9afdc76f2'),
		 ('45678912W','Worker2','acab','worker2@gmail.com','worker2','970698013df8dc9416fc37a800e096da5ea01d7d6abd44a95b1817cbf9b16d0b26313080df64f39b6fe8c384bea07665d437085c90f93e6252b144ffe0adba59');

INSERT INTO `worker`(`dni`) VALUES ('78945612W'),('45678912W');
INSERT INTO `businessman`(`dni`) VALUES ('12345678A'),('87654321A');

INSERT INTO `ofert`(`name`, `description`, `ubication`, `salary`, `contractType`, `businessmanId`) 
	VALUES ('Java Programmer','A programmer with experiencie in Java','Cadiz, Spain',1300,'Full-time for indeterminated time',1),
		 ('Waiter','Waiter for a cocktail bar','Madrid, Spain',1200,'Part-time for summer season',2)


