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
 

INSERT INTO `user`(`id`,`dni`, `name`, `surname`, `eMail`, `user`, `password`) 
	VALUES ('1','44058714Y','David','Ramos Ponce','davidramosconil14@gmail.com','david','david'),
		 ('2','12345678A','Businessman1','acab','businessman1@gmail.com','businessman1','businessman1'),
		 ('3','87654321A','Businessman2','acab','businessman2@gmail.com','businessman2','businessman2'),
		 ('4','78945612W','Worker1','acab','worker1@gmail.com','worker1','worker1'),
		 ('5','45678912W','Worker2','acab','worker2@gmail.com','worker2','worker2'),
		 ('6','65412578S','Worker3','acab','worker3@gmail.com','worker3','worker3');

INSERT INTO `worker`(`id`,`userId`) VALUES ('1','4'),('2','5'),('3','6');
INSERT INTO `businessman`(`id`,`userId`) VALUES ('1','2'),('2','3');

INSERT INTO `offer`(`id`,`name`, `description`, `ubication`, `salary`, `contractType`, `businessmanId`) 
	VALUES ('1','Java Programmer','A programmer with experiencie in Java','Cadiz, Spain',1300,'Full-time for indeterminated time',1),
		('2','SQL Programmer','A programmer with experiencie in Java','Cadiz, Spain',1300,'Full-time for indeterminated time',1),
		 ('3','Waiter','Waiter for a cocktail bar','Madrid, Spain',1200,'Part-time for summer season',2);

INSERT INTO `label`(`id`,`name`) VALUES('1','Java'),('2','Full-Stack'),('3','Waiter'),('4','Cocktail'),('5','Lawyer'),('6','woodworker');

INSERT INTO `label_offer`(`offerId`, `labelId`,`obligatory`) VALUES ('1','1',True),('1','2',False),('2','1',True),('2','2',False),('3','3',True),('3','4',False);

INSERT INTO `profile` (`name`,`workerID`) VALUES ('Programmer','1'),('Waiter','2');
INSERT INTO `profile_label` (`profileID`,`labelID`) VALUES ('1','1'),('1','2');

INSERT INTO `alert` (`id`,`profileID`) VALUES ('1','1');

INSERT INTO `knowledge`(`id`, `name`, `fechaInicio`, `fechaFin`, `workerId`,`type`,`place`,`title`) 
	VALUES (1,'Java course','2020/01/01','2020/05/01',1,'Qualification','Cádiz','3 months course'), (2,'Java Work Trainee','2020/05/01','2020/08/01',1,'WorkExperience','Cádiz','Junior Programmer'),
	(3,'SQL Course','2021/01/01','2021/05/01',1,'Qualification','Cádiz','3 months course'),(4,'SQL Work Trainee','2021/05/01','2021/08/01',1,'WorkExperience','Cádiz','Junior Programmer'),
	(5,'Java course','2020/01/01','2020/05/01',2,'Qualification','Cádiz','3 months course'),(6,'Java Work Trainee','2020/05/01','2020/08/01',2,'WorkExperience','Cádiz','Junior Programmer');

INSERT INTO `label_knowledge`(`knowledgeId`, `labelId`) VALUES (1,1),(2,1),(3,2),(4,2),(5,1),(6,1);

