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
    	name VARCHAR(50),
    	CONSTRAINT label_pk PRIMARY KEY (id),
	CONSTRAINT label_uk UNIQUE KEY (name)
);

CREATE OR REPLACE TABLE profile(
	id INT AUTO_INCREMENT,
	name VARCHAR(50),
	workerID INT,
	CONSTRAINT profile_pk PRIMARY KEY (id),
	CONSTRAINT profile_uk UNIQUE KEY(name,workerID),
	CONSTRAINT profile_worker_fk FOREIGN KEY (workerId) REFERENCES worker(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE profile_label(
	id INT AUTO_INCREMENT,
    	labelId INT,
    	profileId INT, 
    	CONSTRAINT label_profile_pk PRIMARY KEY (id),
	CONSTRAINT label_profile_uk UNIQUE KEY (labelId,profileId),
    	CONSTRAINT label_profile_label_fk FOREIGN KEY (labelId) REFERENCES label(id) ON DELETE CASCADE,
    	CONSTRAINT label_profile_profile_fk FOREIGN KEY (profileId) REFERENCES profile(id) ON DELETE CASCADE
); 


CREATE OR REPLACE TABLE alert(
	id INT AUTO_INCREMENT,
    	profileID INT,
    	CONSTRAINT alert_pk PRIMARY KEY (id),
	CONSTRAINT alert_uk UNIQUE KEY(profileID),
	CONSTRAINT alert_profile_fk FOREIGN KEY (profileID) REFERENCES profile(id) ON DELETE CASCADE 
);

CREATE OR REPLACE TABLE offer(
	id INT AUTO_INCREMENT,
    	name VARCHAR(30),
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
	obligatory BOOLEAN,
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
    	content VARCHAR(300), 
	candidatureID INT,
	userId INT,
	sendedTime TIMESTAMP,
    	CONSTRAINT message_pk PRIMARY KEY(id),
	CONSTRAINT message_user_fk FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE,
    	CONSTRAINT message_candidature_fk FOREIGN KEY (candidatureId) REFERENCES candidature(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE notification(
	id INT AUTO_INCREMENT,
	userId INT,
	notified boolean,
	CONSTRAINT notification_pk PRIMARY KEY(id),
	CONSTRAINT notification_user_fk FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE new_candidature_notification(
	id INT AUTO_INCREMENT, 
	notificationID INT,
	candidatureID INT,
	CONSTRAINT new_candidature_notification_pk PRIMARY KEY(id),
	CONSTRAINT new_candidature_notification_uk UNIQUE KEY(candidatureID),
	CONSTRAINT new_candidature_notification_notification_fk FOREIGN KEY (notificationID) REFERENCES notification(id) ON DELETE CASCADE,
	CONSTRAINT new_candidature_notification_candidature_fk FOREIGN KEY (candidatureID) REFERENCES candidature(id) ON DELETE CASCADE
); 

CREATE OR REPLACE TABLE candidature_state_changed_notification(
	id INT AUTO_INCREMENT, 
	notificationID INT,
	candidatureID INT,
	CONSTRAINT candidature_state_changed_notification_pk PRIMARY KEY(id),
	CONSTRAINT candidature_state_changed_notification_uk UNIQUE KEY(candidatureID),
	CONSTRAINT candidature_state_changed_notification_notification_fk FOREIGN KEY (notificationID) REFERENCES notification(id) ON DELETE CASCADE,
	CONSTRAINT candidature_state_changed_notification_candidature_fk FOREIGN KEY (candidatureID) REFERENCES candidature(id) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE new_message_notification(
	id INT AUTO_INCREMENT, 
	notificationID INT,
	messageID INT,
	CONSTRAINT new_message_notification_pk PRIMARY KEY(id),
	CONSTRAINT new_message_notification_uk UNIQUE KEY(messageID),
	CONSTRAINT new_message_notification_notification_fk FOREIGN KEY (notificationID) REFERENCES notification(id) ON DELETE CASCADE,
	CONSTRAINT new_message_notification_message_fk FOREIGN KEY (messageID) REFERENCES message(id) ON DELETE CASCADE 
);
 
CREATE OR REPLACE TABLE new_offer_notification(
	id INT AUTO_INCREMENT, 
	notificationID INT,
	offerID INT,
	alertID INT,
	CONSTRAINT new_offer_notification_pk PRIMARY KEY(id),
	CONSTRAINT new_offer_notification_uk UNIQUE KEY(offerID,alertID),
	CONSTRAINT new_offer_notification_notification_fk FOREIGN KEY (notificationID) REFERENCES notification(id) ON DELETE CASCADE,
	CONSTRAINT new_offer_notification_offer_fk FOREIGN KEY (offerID) REFERENCES offer(id) ON DELETE CASCADE,
	CONSTRAINT new_offer_notification_alert_fk FOREIGN KEY (alertID) REFERENCES alert(id) ON DELETE CASCADE
);
 

INSERT INTO `label`(`id`,`name`) VALUES('1','Java Programmer'),('2','SQL Programmer'),('3','Firefigther'),('4','Lawyer'),('5','Mathematician'),('6','Biologist'),('7','Waiter'),('8','Delivery'),('9','Farmer');