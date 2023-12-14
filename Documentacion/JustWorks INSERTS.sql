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
	

