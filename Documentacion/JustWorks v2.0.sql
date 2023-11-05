CREATE OR REPLACE DATABASE justworks;

USE justworks;

CREATE OR REPLACE TABLE user(
	id INT AUTO_INCREMENT,
	dni VARCHAR(9),
    	name VARCHAR(15),
    	surname VARCHAR(30),
    	eMail VARCHAR(50),
    	user VARCHAR(15),
    	password VARCHAR(30),
    	CONSTRAINT usuario_pk PRIMARY KEY (id),
	CONSTRAINT usuario_uk_dni UNIQUE KEY (dni),
	CONSTRAINT usuario_uk_user UNIQUE KEY (user),
	CONSTRAINT usuario_uk_email UNIQUE KEY (eMail)
);

CREATE OR REPLACE TABLE worker(
    	id INT AUTO_INCREMENT,
	userId INT,
    	CONSTRAINT worker_pk PRIMARY KEY (id),
    	CONSTRAINT worker_uk UNIQUE KEY (userId),
    	CONSTRAINT worker_user_fk FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE businessman(
	id INT AUTO_INCREMENT,
	userId INT,
    	CONSTRAINT businessman_pk PRIMARY KEY (id),
    	CONSTRAINT businessman_uk UNIQUE KEY (userId),
    	CONSTRAINT businessman_user_fk FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE alert(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	workerId INT,
    	CONSTRAINT alert_pk PRIMARY KEY (id),
    	CONSTRAINT alert_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
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
    	CONSTRAINT label_alert_pk PRIMARY KEY (id),
    	CONSTRAINT label_alert_uk UNIQUE  KEY (labelId,alertId),
    	CONSTRAINT label_alert_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
    	CONSTRAINT label_alert_alert_fk FOREIGN KEY (alertId) REFERENCES alert(id) ON DELETE CASCADE
);	


CREATE OR REPLACE TABLE offer(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	description VARCHAR(30),
   	ubication VARCHAR(25),
    	salary INT,
    	contractType VARCHAR(10),
    	businessmanId INT,
    	CONSTRAINT offer_pk PRIMARY KEY (id),
	CONSTRAINT offer_uk UNIQUE KEY(name,businessmanID),
    	CONSTRAINT offer_businessman_fk FOREIGN KEY (businessmanId) REFERENCES businessman(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE label_offer(
	id INT AUTO_INCREMENT,
    	labelId INT,
    	offerId INT,
    	CONSTRAINT label_offer_pk PRIMARY KEY (id),
	CONSTRAINT label_offer_uk UNIQUE KEY (labelId,offerId),
    	CONSTRAINT label_offer_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
    	CONSTRAINT label_offer_offer_fk FOREIGN KEY (offerId) REFERENCES offer(id) ON DELETE CASCADE
); 

CREATE OR REPLACE TABLE knowledge(
	id INT AUTO_INCREMENT,
   	name VARCHAR(15),
   	fechaInicio DATE,
   	fechaFin DATE,
    	workerId INT,
    	CONSTRAINT knowledge_pk PRIMARY KEY (id),
    	CONSTRAINT knowledge_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
); 


CREATE OR REPLACE TABLE work_experience(
    	id INT AUTO_INCREMENT,
    	workName VARCHAR(20),
    	company VARCHAR(15),
      knowledgeID INT,
    	CONSTRAINT work_experience_pk PRIMARY KEY (id),
    	CONSTRAINT work_experience_knowledge_fk FOREIGN KEY (knowledgeID) REFERENCES knowledge(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE qualification(
    	id INT AUTO_INCREMENT,
    	qualificationName VARCHAR(20),
    	studyCenter VARCHAR(15),
      knowledgeID INT,
    	CONSTRAINT qualification_pk PRIMARY KEY (id),
    	CONSTRAINT qualification_knowledge_fk FOREIGN KEY (knowledgeID) REFERENCES knowledge(id) ON DELETE CASCADE
); 

CREATE OR REPLACE TABLE label_knowledge(
	id INT AUTO_INCREMENT,
    	labelId INT,
    	knowledgeId INT,
    	CONSTRAINT label_knowledge_pk PRIMARY KEY (id),
	CONSTRAINT label_knowledge_uk UNIQUE KEY (labelId,knowledgeId),
    	CONSTRAINT label_knowledge_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
    	CONSTRAINT label_knowledge_knowledge_fk FOREIGN KEY (knowledgeId) REFERENCES knowledge(id) ON DELETE CASCADE
); 

CREATE OR REPLACE TABLE candidature(
	id INT AUTO_INCREMENT,
    	realizationDate date,
    	offerID INT, 
    	CONSTRAINT candidature_pk PRIMARY KEY (id),
    	CONSTRAINT candidature_offer_fk FOREIGN KEY (offerId) REFERENCES offer(id)
);


CREATE OR REPLACE TABLE message(
	id INT AUTO_INCREMENT,
    	content VARCHAR(100),
    	sender BOOLEAN,
   	sendDate DATE,
	candidatureID INT,
    	CONSTRAINT message_pk PRIMARY KEY(id),
    	CONSTRAINT message_candidatura_fk FOREIGN KEY (candidatureId) REFERENCES candidature(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE response(
	id INT AUTO_INCREMENT,
	labelID INT,
	candidatureID INT,
	CONSTRAINT response_label_pk PRIMARY KEY (id) ,
	CONSTRAINT candidature_knowledge_uk UNIQUE KEY(labelID,candidatureID),
	CONSTRAINT response_label_fk FOREIGN KEY (labelID) REFERENCES label(id),
	CONSTRAINT response_candidature_fk FOREIGN KEY (candidatureID) REFERENCES candidature(id) 
);

CREATE OR REPLACE TABLE response_knowledge(
	id INT AUTO_INCREMENT,
    	responseId INT,
    	knowledgeId INT,
    	CONSTRAINT response_knowledge_pk PRIMARY KEY (id),
	CONSTRAINT response_knowledge_uk UNIQUE KEY (responseId,knowledgeId),
    	CONSTRAINT response_knowledge_response_fk FOREIGN KEY (responseId) REFERENCES response(id),
    	CONSTRAINT response_knowledge_knowledge_fk FOREIGN KEY (knowledgeId) REFERENCES knowledge(id)
);

INSERT INTO `user`(`id`,`dni`, `name`, `surname`, `eMail`, `user`, `password`) 
	VALUES ('1','44058714Y','David','Ramos Ponce','davidramosconil14@gmail.com','david','david'),
		 ('2','12345678A','Businessman1','acab','businessman1@gmail.com','businessman1','businessman1'),
		 ('3','87654321A','Businessman2','acab','businessman2@gmail.com','businessman2','businessman2'),
		 ('4','78945612W','Worker1','acab','worker1@gmail.com','worker1','worker1'),
		 ('5','45678912W','Worker2','acab','worker2@gmail.com','worker2','worker2');

INSERT INTO `worker`(`userId`) VALUES ('4'),('5');
INSERT INTO `businessman`(`userId`) VALUES ('2'),('3');

INSERT INTO `offer`(`id`,`name`, `description`, `ubication`, `salary`, `contractType`, `businessmanId`) 
	VALUES ('1','Java Programmer','A programmer with experiencie in Java','Cadiz, Spain',1300,'Full-time for indeterminated time',1),
		 ('2','Waiter','Waiter for a cocktail bar','Madrid, Spain',1200,'Part-time for summer season',2);

INSERT INTO `label`(`id`,`name`) VALUES('1','Java'),('2','Full-Stack'),('3','Waiter'),('4','Cocktail');

INSERT INTO `label_offer`(`offerId`, `labelId`) VALUES ('1','1'),('1','2'),('2','3'),('2','4');


