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

INSERT INTO `user`(`id`,`dni`, `name`, `surname`, `eMail`, `user`, `password`)
	VALUES   ('1','44058714Y','David','Ramos Ponce','davidramosconil14@gmail.com','david','david'),
		 ('2','87509426S','Businessman1',' BSurname1','businessman1@gmail.com','businessman1','businessman1'),
		 ('3','24687941V','Businessman2','BSurname2','businessman2@gmail.com','businessman2','businessman2'),
		 ('4','38398523T','Businessman3','BSurname3','businessman3@gmail.com','businessman3','businessman3'),
		 ('5','76126715G','Businessman4','BSurname4','businessman4@gmail.com','businessman4','businessman4'),
		 ('6','13724323Q','Businessman5','BSurname5','businessman5@gmail.com','businessman5','businessman5'),
		 ('7','14093915K','Businessman6','BSurname6','businessman6@gmail.com','businessman6','businessman6'),
		 ('8','23029560M','Businessman7','BSurname7','businessman7@gmail.com','businessman7','businessman7'),
		 ('9','26463599Y','Worker1','WSurname1','worker1@gmail.com','worker1','worker1'),
		 ('10','05343113Y','Worker2','WSurname2','worker2@gmail.com','worker2','worker2'),
		 ('11','64089512N','Worker3','WSurname3','worker3@gmail.com','worker3','worker3'),
		 ('12','05398824B','Worker4','WSurname4','worker4@gmail.com','worker4','worker4'),
		 ('13','15628962W','Worker5','WSurname5','worker5@gmail.com','worker5','worker5'),
		 ('14','14721758X','Worker6','WSurname6','worker6@gmail.com','worker6','worker6'),
		 ('15','97817767D','Worker7','WSurname7','worker7@gmail.com','worker7','worker7');
 

INSERT INTO `businessman`(`id`,`userId`) VALUES ('1','2'),('2','3'),('3','4'),('4','5'),('5','6'),('6','7'),('7','8');
INSERT INTO `worker`(`id`,`userId`) VALUES ('1','9'),('2','10'),('3','11'),('4','12'),('5','13'),('6','14'),('7','15');

INSERT INTO `offer`(`id`,`name`, `description`, `ubication`, `salary`, `contractType`, `businessmanId`) 
	VALUES  ('1','Full-Stack programmer','A Full-Stack programmer with experiencie in Java and SQL','Madrid, Spain',1300,'Full-time',1),
		('2','Forest Firefigther','Firefigther in a forest in summer','Jerez, Spain',1700,'Full-time for the summer season',2),
		('3','Lawyer ','Lawyer in an international law firm','Homeworking',2300,'Full-time',3),
		('4','Marine Biologist','Marine biologist to embark for 3 months in Africa with matemathical knowledge','Africa',2300,'Full-time three months',4), 
		('5','Waiter','Waiter for a cocktail bar','Madrid, Spain',1100,'Part-time for summer season',5),
		('6','Glovo Delivery','Delivery for the app Glovo','Madrid, Spain',1,'Self-employed',6);

INSERT INTO `label_offer`(`offerId`, `labelId`,`obligatory`) 
	VALUES  ('1','1',True),('1','2',True),
		('2','3',True),
		('3','4',True),
		('4','5',False),('4','6',True),
		('5','7',True),
		('6','8',True);

INSERT INTO `knowledge`(`id`, `name`, `fechaInicio`, `fechaFin`,`type`,`place`,`title`,`workerId`) 
	VALUES  (1,'Java Course','2020/01/01','2020/04/01','Qualification','Cádiz, Spain','3 months course',1), (2,'Java Work Trainee','2020/04/02','2020/07/02','WorkExperience','Cádiz, Spain','Junior Programmer',1),
		(3,'SQL Course','2021/01/01','2021/04/01','Qualification','Cádiz, Spain','3 months course',1),(4,'SQL Work Trainee','2020/07/02','2020/07/02','WorkExperience','Cádiz, Spain','Junior Programmer',1),
		(5,'Firefigther Course','2021/01/01','2022/01/01','Qualification','Madrid, Spain','1 year course',2),
		(6,'Lawyer Degree','2019/01/01','2023/04/01','Qualification','Barcelona, Spain','Lawyer Degree',3),(7,'Lawyer Trainee','2023/04/02','2023/11/02','WorkExperience','Barcelona, Spain','Lawyer Assitance',3),
		(8,'Biologist Degree','2019/01/01','2023/04/01','Qualification','Murcia, Spain','Biologist Degree',4),(9,'Biologist Trainee','2023/04/02','2023/11/02','WorkExperience','Barcelona, Spain','Biologist Assitance',4),
		(10,'Waiter in BurguerKing','2020/04/02','2022/07/02','WorkExperience','Conil, Spain','Burguer King Worker',5);
		
		

INSERT INTO `label_knowledge`(`knowledgeId`, `labelId`) 
	VALUES  (1,1),(2,1),
		(3,2),(4,2),
		(5,3),
		(6,4),(7,4),
		(8,6),(9,6),
		(10,7);
	








