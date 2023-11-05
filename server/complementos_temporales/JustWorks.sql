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



CREATE OR REPLACE TABLE label(
	id INT AUTO_INCREMENT,
    	name VARCHAR(15),
    	CONSTRAINT label_pk PRIMARY KEY (id),
	CONSTRAINT label_uk UNIQUE KEY (name)
);

CREATE OR REPLACE TABLE alert(
	id INT AUTO_INCREMENT,
    	workerId INT,
	labelId INT,
    	CONSTRAINT alert_pk PRIMARY KEY (id),
	CONSTRAINT alert_uk UNIQUE KEY(workerId,labelId),
	CONSTRAINT alert_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
    	CONSTRAINT alert_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
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
	place VARCHAR(20),
	title VARCHAR(20),
	type VARCHAR(20),
   	fechaInicio DATE,
   	fechaFin DATE,
    	workerId INT,
    	CONSTRAINT knowledge_pk PRIMARY KEY (id),
    	CONSTRAINT knowledge_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
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
	state VARCHAR(20), 
    	offerId INT, 
	workerId INT,
    	CONSTRAINT candidature_pk PRIMARY KEY (id),
	CONSTRAINT candidature_uk UNIQUE KEY (offerId,workerId),
    	CONSTRAINT candidature_offer_fk FOREIGN KEY (offerId) REFERENCES offer(id) ON DELETE CASCADE,
	CONSTRAINT candidature_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
);


CREATE OR REPLACE TABLE message(
	id INT AUTO_INCREMENT,
    	content VARCHAR(100),
    	sender BOOLEAN,
	candidatureID INT,
    	CONSTRAINT message_pk PRIMARY KEY(id),
    	CONSTRAINT message_candidatura_fk FOREIGN KEY (candidatureId) REFERENCES candidature(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE notification(
	id INT AUTO_INCREMENT,
	labelId INT,
	offerId INT,
    	alertId INT,
	notified boolean,
	CONSTRAINT notification_pk PRIMARY KEY(id),	
	CONSTRAINT notification_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
	CONSTRAINT notification_offer_fk FOREIGN KEY (offerId) REFERENCES offer(id) ON DELETE CASCADE,
	CONSTRAINT notification_alert_fk FOREIGN KEY (alertId) REFERENCES alert(id) ON DELETE CASCADE
);
 

INSERT INTO `user`(`id`,`dni`, `name`, `surname`, `eMail`, `user`, `password`) 
	VALUES ('1','44058714Y','root','rooooot','root@root.com','root','root'); 
 

INSERT INTO `label`(`id`,`name`) VALUES('1','Java'),('2','Full-Stack'),('3','Waiter'),('4','Cocktail'),('5','Lawyer'),('6','woodworker');
 

