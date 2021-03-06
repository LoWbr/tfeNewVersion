
INSERT INTO activity_type (name,met) VALUES
('Golf',4.8),
('Volleyball',6),
('Cycling',7),
('Running',7.8),
('Swimming',5.5),
('Tennis',7.3),
('Climbing',8.0),
('Moutain Bike',8.5),
('Judo',10.3);

INSERT INTO level (name, place, maximum_threshold, ratio_points) VALUES
('Beginner',1,3000,1),
('Active',2,5000,0.8),
('Sporstman',3,10000,0.5),
('Athlete',4,15000,0.4),
('Hero',5,20000,0.3);

INSERT INTO sports_man (first_name,last_name,description,date_of_birth,weight,points,email,password, level_id,blocked) VALUES
('Laurent','Weber','Chevelu Sportif','1990-05-29',79,2900,'laurent@gmail.com','$2y$10$e/M4T66Ioz3rZ0fltX7qpeLzonDbP/sD60zehJ77t0SoJUCF.WGMa',3,false),
('Michael','Atlas','Photograhe en tout genre','1988-04-01',90.3,32,'mike@gmail.com','$2y$10$0w8iBx3F0.9N9x5eGgy73eqhkDRcKXGnoZQyqvFZ9KC0XEeyHRi0K',5,false),
('Geoffrey','Moyens','Militaire et Patate Mariée','1983-02-02',95,40,'geof@gmail.com','$2y$10$4g0uGcj7IjljDWoCY5e8X.T2GbVP2e/RPs65.MbUvYECccKlJiQqW',4,true),
('Romain','Monsterlet','Animateur et Flûtiste','1990-12-06',83,84,'ro@gmail.com','$2y$10$FbazGLhM/vvjhS8ykF5neu58q9cdQB02b1DVVdPfHY47S6zoAw3US',2,false),
('Baptiste','Marck','Orval et Web','1990-10-29',70,43,'baptiste@gmail.com','$2y$10$NbmUdZTlCUZxXtWCmMxhjOCEbEXa1aASSMueCoQYcfNmjxtPhU8m6',3,false),
('Troy','Devrieze','Crêtu Blondinet','1990-07-29',65,36,'troy@gmail.com','$2y$10$.Xp0.U7Xym8UzPNIKTO/Ted7AZckkHRMWJ4tybWEgZVRXD.5WGkm2',1,false),
('Paul','Guthrie','nunc est, mollis non, cursus non, egestas a, dui. Cras pellentesque. Sed dictum. Proin eget','96-01-22',79,5053,'sit.amet@gmail.com','CKD25IWE5FM',3,true),
('Walter','Norris','tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis.','84-05-25',92,12845,'nunc.In.at@gmail.com','SRO11BXG6FN',4,true),
('Neve','Barber','posuere cubilia Curae; Donec tincidunt. Donec vitae erat vel pede blandit congue. In scelerisque scelerisque','00-02-04',84,6862,'Aenean@gmail.com','OHC53KHT1JT',3,true),
('Garrett','Wynn','mauris, aliquam eu, accumsan sed, facilisis vitae, orci. Phasellus dapibus quam quis diam. Pellentesque habitant','96-04-18',56,12732,'Sed@lacus.com','YCP59VXV0VG',4,true),
('Autumn','Randall','malesuada id, erat. Etiam vestibulum massa rutrum magna. Cras convallis convallis dolor. Quisque tincidunt pede','99-01-18',87,8377,'et.magnis@Nulla.org','FRX27HBS7MZ',3,false),
('Lucius','Salazar','ornare egestas ligula. Nullam feugiat placerat velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl.','79-05-12',86,12903,'tempor@gmail.com','EJL30HMO5NK',4,true),
('Uriah','Ellison','penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse tristique neque','72-05-19',55,3205,'massa.Integer@gmail.com','OWN21MQN7GR',2,true),
('Keelie','Mcdaniel','purus ac tellus. Suspendisse sed dolor. Fusce mi lorem, vehicula et, rutrum eu, ultrices sit','95-03-12',100,7885,'luctus@gmail.com','EVD33XJQ2RI',3,true),
('Declan','Medina','sit amet, faucibus ut, nulla. Cras eu tellus eu augue porttitor interdum. Sed auctor odio','73-12-18',84,3372,'tellus.mollis@magna.ca','PKV87LFS1FM',2,true),
('Noel','Potts','et, rutrum non, hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer, cursus et,','76-04-09',53,3272,'a.arcu.Sed@Nunc.net','EFH58SRC4VW',2,true),
('Elvis','Dejesus','gravida sagittis. Duis gravida. Praesent eu nulla at sem molestie sodales. Mauris blandit enim consequat','70-09-30',67,4803,'sed.facilisis@gmail.com','UNK71SIL5BT',2,false),
('Linus','Underwood','ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis','70-07-08',92,3001,'velit.in@gmail.com','VVT37PQD0WP',2,true),
('Silas','Harvey','ornare, elit elit fermentum risus, at fringilla purus mauris a nunc. In at pede. Cras','82-11-06',78,5294,'convallis@gmail.com','EZH11BBB6MR',3,true),
('Zorita','Golden','nec, mollis vitae, posuere at, velit. Cras lorem lorem, luctus ut, pellentesque eget, dictum placerat,','83-02-08',67,15844,'egestas@et.net','EFD27NKP4IG',5,true),
('Alfreda','Bray','Duis a mi fringilla mi lacinia mattis. Integer eu lacus. Quisque imperdiet, erat nonummy ultricies','93-10-28',62,5384,'tellus.leo@gmail.com','WRP18KMX0QM',3,true),
('Len','Watson','auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in,','82-06-14',71,9765,'enim.mi@Suspendisse.co.uk','QYS22CZU5PP',3,true),
('MacKenzie','Marks','enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas a, dui. Cras','82-02-19',54,8927,'elit@gmail.com','VIN86IHY4AN',3,true),
('April','Vang','Donec feugiat metus sit amet ante. Vivamus non lorem vitae odio sagittis semper. Nam tempor','80-08-08',62,8460,'interdum@Aenean.com','LCY79OXJ6JL',3,false),
('Nora','Berger','facilisis non, bibendum sed, est. Nunc laoreet lectus quis massa. Mauris vestibulum, neque sed dictum','73-10-29',55,6851,'Proin@gmail.com','EOW89GPM1BM',3,true),
('Ivan','Roman','mi enim, condimentum eget, volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean gravida','72-11-03',98,8009,'lacinia@gmail.com','YXJ76TID8HH',3,true),
('Cheyenne','Prince','eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante','90-06-26',63,12180,'fringilla@gmail.com','VVB08EXN3IC',4,true),
('Rachel','Ramos','nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices','96-02-24',76,11998,'vitae.egestas@gmail.com','PVN95XJD5JG',4,false),
('Chester','Morse','arcu imperdiet ullamcorper. Duis at lacus. Quisque purus sapien, gravida non, sollicitudin a, malesuada id,','83-02-04',56,8107,'risus.Donec@gmail.com','OEM29WAL6JR',3,true),
('Grady','Moreno','tempor, est ac mattis semper, dui lectus rutrum urna, nec luctus felis purus ac tellus.','78-06-28',62,11213,'amet@purus.co.uk','NFX82HBP9MY',4,true),
('Charity','Cummings','dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus','78-04-07',70,2485,'euismod.ac@sodales.com','ZPM73PVV6EF',1,true),
('Sonia','Reyes','dolor sit amet, consectetuer adipiscing elit. Aliquam auctor, velit eget laoreet posuere, enim nisl elementum','94-02-25',71,3793,'ullamcorper@gmail.com','LBZ09YIT7YR',2,true),
('Amaya','Wolfe','penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean eget magna. Suspendisse tristique neque','01-02-23',82,4357,'luctus.sit@Sed.com','MQU26EFL9HQ',2,false),
('Winter','Todd','ut ipsum ac mi eleifend egestas. Sed pharetra, felis eget varius ultrices, mauris ipsum porta','79-04-18',81,10268,'mollis@neque.net','PTC51SOM3LV',4,true),
('Indira','Bonner','Duis mi enim, condimentum eget, volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula. Aenean','92-11-19',90,1584,'aliquet.@gmail.com','QYY54LBQ6KR',1,true);

INSERT INTO address(number, street, postal_code, city, country) VALUES
(18,'Rue Germinal',5000,'Namur','Belgique'),
(170,'Rue du bout du Monde',6600,'Bastogne','Belgique'),
(46,'Rue des Paquerettes',8400,'Ostende','Belgique'),
(25,'Rue du Loup',50441,'Cologne','Allemagne'),
(11,'Rue Mercier',4700,'Eupen','Belgique'),
(229,'Rue du val vallonée',52100,'Saint-Dizier','France'),
(99,'Appaed Ave',1500,'HalVilvorde','Belgique'),
(91,'Auctor Ave',4434,'Alleur','Belgique'),
(74,'Semper Impasse',04358,'Leipzig','Allemagne'),
(65,'Peial Impasse',8750,'Wingene','Belgique'),
(111,'Tempus Rd',69004,'Lyon','France'),
(27,'Ac Route',6616,'Bastogne','Belgique'),
(98,'Vitae Impasse',2586,'Beerzel','France'),
(100,'Cursus Avenue',44135,'Dortmund','Allemagne'),
(28,'Nulla Impasse',4945,'Aywaille','Belgique'),
(24,'Erat Avenue',4276,'Hannut','Belgique'),
(81,'Tempus Rd',86199,'Augsburg','Allemagne'),
(97,'Nunc Av',29000,'Quimper','France'),
(76,'Nulla Rue',64283,'Darmstadt','Allemagne'),
(57,'Diam Rd',5925,'Chevetogne','Belgique');

--Old Events
INSERT INTO activity (name,description, type_activity_id, creator_id,duration,open,over,minimum_level_id, maximum_level_id, planned_to,hour, address_id) VALUES
('Initiation Crawl','Swimming, Beginning',5,1,90,true,true,1,1,'2020-05-10','14:00:00',3),
('Namur-Ottiginies','45km, intense',3,2,60,true,true,2,4,'2020-06-15','09:30:00',1),
('Cross au Bois de Malonne','Au travers des arbres',4,1,180,true,true,1,2,'2020-06-04','08:00:00',1),
('Descente de la Meuse','Traveling au travers de la flotte',5,1,45,true,false,1,3,'2020-06-15','13:30:00',6);

--Current Events
INSERT INTO activity (name,description, type_activity_id, creator_id,duration,open,over,minimum_level_id, maximum_level_id, planned_to, hour, address_id) VALUES
('Initiation au Papillon','Découverte, pour les non-initiés, de cette intense pratique. Brasse et Crawl non-nécessaires.' ,5,1,90,true,false,1,3,'2020-06-20','14:00:00',5),
('Footing Mosan','Along the river',4,2,60,true,false,1,2,'2020-07-15','09:30:00',6),
('Vélo en Folie','Fun and Fun, in wilderness',3,3,180,true,false,1,1,'2020-07-04','08:00:00',2),
('Run & Beer','Plus long, plus long, plus long, plus long',4,1,45,true,false,1,2,'2020-06-25','17:30:00',4),
('Etretat Racing Golf','dui. Fusce diam nunc, ullamcorper eu, euismod ac, fermentum vel',1,6,150,true,false,1,2,'20-08-25','10:00:00',1),
('Clubs en Folie','feugiat nec, diam. Duis mi enim, condimentum eget, volutpat ornare',1,4,100,true,false,2,3,'21-02-28','18:00:00',20),
('Balls type 2','leo elementum sem, vitae aliquam eros turpis non enim. Mauris',1,1,160,true,false,3,3,'20-08-16','14:00:00',8),
('Bicycle Party','neque sed sem egestas blandit. Nam nulla magna, malesuada vel',3,1,80,true,false,2,3,'21-05-09','18:00:00',8),
('Basket Party','metus. In nec orci. Donec nibh. Quisque nonummy ipsum non',4,2,60,true,false,5,5,'21-02-17','10:00:00',13),
('4km Crawl','Donec sollicitudin adipiscing ligula. Aenean gravida nunc sed pede. Cum',5,3,130,true,false,3,4,'21-04-19','14:00:00',15),
('Géronsart Tournament','velit. Quisque varius. Nam porttitor scelerisque neque. Nullam nisl. Maecenas',6,3,150,true,false,3,4,'21-04-14','10:00:00',4),
('Test 18 trous','lorem, eget mollis lectus pede et risus. Quisque libero lacus,',1,3,130,true,false,4,4,'21-01-10','18:00:00',12),
('Les vélos en folie','egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est',3,2,80,true,false,2,3,'21-01-16','18:00:00',4),
('More and More feet','nunc sit amet metus. Aliquam erat volutpat. Nulla facilisis. Suspendisse',4,4,140,true,false,1,2,'20-10-25','18:00:00',19),
('Jogging +1000','sollicitudin orci sem eget massa. Suspendisse eleifend. Cras sed leo',4,1,170,true,false,1,1,'20-10-21','21:00:00',3),
('20km Race challenge','vitae, aliquet nec, imperdiet nec, leo. Morbi neque tellus, imperdiet',4,1,135,true,false,1,1,'20-08-10','10:00:00',6),
('Earth and water','in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus',8,4,100,true,false,1,2,'21-06-19','14:00:00',7),
('Volley Session 1','ac turpis egestas. Fusce aliquet magna a neque. Nullam ut',2,1,100,true,false,2,3,'20-07-09','21:00:00',11),
('24h de raquette','sagittis semper. Nam tempor diam dictum sapien. Aenean massa. Integer',6,1,170,true,false,3,3,'20-10-14','21:00:00',18),
('Black Forest Challenge','leo. Morbi neque tellus, imperdiet non, vestibulum nec, euismod in',8,3,160,true,false,4,4,'20-11-15','18:00:00',2),
('Wheels and Wheels','magna nec quam. Curabitur vel lectus. Cum sociis natoque penatibus',3,2,170,true,false,5,5,'21-01-02','10:00:00',18),
('Unofficial fight','ultrices iaculis odio. Nam interdum enim non nisi. Aenean eget',9,2,120,true,false,3,3,'21-04-30','21:00:00',10),
('Tournoi on the beach','ut ipsum ac mi eleifend egestas. Sed pharetra, felis eget',2,1,100,true,false,3,3,'20-10-26','10:00:00',16),
('Cross Mosan','ipsum cursus vestibulum. Mauris magna. Duis dignissim tempor arcu. Vestibulum',4,3,120,true,false,4,5,'21-04-07','18:00:00',9),
('Stamina Swimming','Sed molestie. Sed id risus quis diam luctus lobortis. Class',5,1,70,true,false,3,4,'20-12-26','10:00:00',7),
('Festival fluo','dolor. Donec fringilla. Donec feugiat metus sit amet ante. Vivamus',6,4,140,true,false,2,3,'20-12-03','14:00:00',13),
('Deux roues, pas plus','mi eleifend egestas. Sed pharetra, felis eget varius ultrices, mauris',3,6,140,true,false,1,1,'20-11-14','21:00:00',1),
('8hClimbing challenge','mauris elit, dictum eu, eleifend nec, malesuada ut, sem. Nulla',7,6,160,true,false,1,1,'21-06-01','18:00:00',17),
('Orange Belt Compet','Quisque purus sapien, gravida non, sollicitudin a, malesuada id, erat.',9,4,120,true,false,1,2,'21-01-23','10:00:00',5),
('Tous balles dehors!','velit eget laoreet posuere, enim nisl elementum purus, accumsan interdum',1,4,120,true,false,2,3,'20-09-18','14:00:00',9),
('Bicyle Race','eget, volutpat ornare, facilisis eget, ipsum. Donec sollicitudin adipiscing ligula.',3,1,140,true,false,3,4,'21-02-26','18:00:00',14),
('K2 Challenge','mi. Aliquam gravida mauris ut mi. Duis risus odio, auctor',7,4,100,true,false,2,2,'21-02-16','14:00:00',6),
('Apalache Trash','elit elit fermentum risus, at fringilla purus mauris a nunc.',8,2,100,true,false,4,5,'20-11-07','18:00:00',7);

INSERT INTO activity_participants(registered_activities_id, participants_id) VALUES
(1,1),
(1,4),
(1,6),
(2,2),
(2,3),
(3,1),
(3,4),
(4,1),
(4,2),
(4,3),
(4,4),
(4,5),
(4,6),
(8,6),
(8,2),
(5,1);


INSERT INTO activity_candidates(activity_id, candidates_id) VALUES
(8,2);

--Statistic
INSERT INTO statistic (earned_points,energy_expenditure,activity_id, sports_man_id) VALUES
(35,350,1,1),
(38,380,1,4),
(32,320,1,5),
(26,260,1,6),
(20,200,2,2),
(25,250,2,3),
(25,250,3,1),
(32,320,3,4),
(12,120,1,2),
(15,150,1,3),
(14,140,2,4),
(11,110,2,5),
(10,100,3,6);

INSERT INTO sports_man_contacts (sports_man_id,contacts_id) VALUES
(2,1),
(2,3),
(1,3),
(1,6),
(1,12),
(1,19),
(1,20),
(1,7),
(1,4),
(3,4),
(2,6),
(5,1),
(5,2),
(5,3),
(5,9),
(5,13),
(5,14)
;
INSERT INTO role (name) VALUES
('ROLE_SIMPLY'),
('ROLE_CONFIRMED'),
('ROLE_ADMINISTRATOR');

INSERT INTO user_role (users_id,roles_id) VALUES
(1,3),
(1,2),
(1,1),
(2,2),
(2,1),
(3,2),
(3,1),
(4,1),
(4,2),
(5,1),
(6,1),
(6,2),
(8,1),
(9,1),
(10,1),
(11,1),
(12,1),
(13,1),
(14,1),
(15,1),
(16,1),
(17,1),
(18,1),
(19,1),
(20,1),
(21,1),
(22,1),
(23,1),
(24,1),
(25,1),
(26,1),
(27,1),
(28,1),
(29,1),
(30,1),
(31,1),
(32,1),
(33,1),
(34,1),
(35,1);

INSERT INTO message (about,content,time_of_dispatch, author_id) VALUES
('Introduction','quam, elementum at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et pede. Nunc sed','20-04-05 08:03:00',5),
('Hello!','dapibus id, blandit at, nisi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur','19-06-28 09:26:00',5),
('Correction','Morbi non sapien molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam ornare, libero at','19-07-29 16:27:00',3),
('About the crawl','odio. Phasellus at augue id ante dictum cursus. Nunc mauris elit, dictum eu, eleifend nec','19-07-18 14:02:00',4),
('Join us','ut, pellentesque eget, dictum placerat, augue. Sed molestie. Sed id risus quis diam luctus lobortis.','20-05-12 23:26:00',5),
('Do you see that?','vitae, posuere at, velit. Cras lorem lorem, luctus ut, pellentesque eget, dictum placerat, augue. Sed','20-03-12 14:18:00',5),
('Warning','amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non','19-12-08 22:16:00',1),
('About the last activity','dolor sit amet, consectetuer adipiscing elit. Etiam laoreet, libero et tristique pellentesque, tellus sem mollis','19-10-03 06:34:00',1),
('Do you know him?','egestas nunc sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum','19-12-10 19:52:00',1),
('For the conflict','pretium et, rutrum non, hendrerit id, ante. Nunc mauris sapien, cursus in, hendrerit consectetuer, cursus','19-11-19 11:44:00',1),
('Good news!','pretium aliquet, metus urna convallis erat, eget tincidunt dui augue eu tellus. Phasellus elit pede','20-02-27 07:23:00',1);

INSERT INTO message_addressees (received_messages_id,addressees_id) VALUES
(1,1),
(2,1),
(3,1),
(4,1),
(5,1),
(5,2),
(5,3),
(6,5),
(7,2),
(7,2),
(8,25),
(8,12),
(9,23),
(10,4),
(11,6);

INSERT INTO news (content, seen, type, activity_id, source_id, target_id) VALUES

('ut, pellentesque eget, dictum placerat, augue. Sed molestie. Sed...',false,'MESSAGE_SEND',null,5,1),
('Romain rejected your demand for the activity : Clubs en Folie',false,'REFUSED_REGISTRATION',10,4,5),
('ut, pellentesque eget, dictum placerat, augue. Sed molestie. Sed...',false,'MESSAGE_SEND',null,5,2),
('dapibus id, blandit at, nisi. Cum sociis natoque penatibus...',false,'MESSAGE_SEND',null,5,1),
('Troy rejected your demand for the activity : Etretat Racing Golf',false,'REFUSED_REGISTRATION',9,6,4),
('Morbi non sapien molestie orci tincidunt adipiscing....',false,'MESSAGE_SEND',null,3,1),
('Laurent has accepted your demand for the activity : Cross au bois de Malonne',false,'VALIDED_REGISTRATION',3,1,5),
('ET, amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat....',false,'MESSAGE_SEND',null,2,1),
('Michael rejected your demand for the activity : Basket Party',false,'REFUSED_REGISTRATION',13,2,5),
('The event Cross au bois de Malonne is now closed',false,'DONE_EVENT',3,1,5),
('Neve Barber applied for the the confirmed Role.',false,'APPLY_AS_CONFIRMED',null,9,1),
('Lucius Salazar applied for the the confirmed Role.',false,'APPLY_AS_CONFIRMED',null,12,1),
('Michael has accepted your demand for the activity : Basket Party',false,'VALIDED_REGISTRATION',13,2,3),
('Nora Berger applied for the the confirmed Role.',false,'APPLY_AS_CONFIRMED',null,25,1),
('Laurent left the following activity: Balls type 2',false,'PARTICIPANT_DROPOUT',7,1,2),
('Baptiste left the following activity: Bicycle Party',false,'PARTICIPANT_DROPOUT',8,5,1);

INSERT INTO promotion_request (applier_id,in_demand_id) VALUES

(9,2),
(12,2),
(25,2);

INSERT INTO topic (content,date, author_id) VALUES

('Attention, modifications en vue : en cas de problèmes, relancer votre navigateur.','19-04-03 11:03:12',1),
('! Avis!! La 100e activité a vu le jour sur la plateforme! Félicitations!','20-01-02 16:15:45',1),
('Bientôt, sur la plateforme : ajout de nouveaux types de sports prévu sous peu!','20-02-06 21:56:02',1);

INSERT INTO comment (content,date, activity_id, author_id) VALUES

('Du matos est nécessaire, ou tout est compris?','20-05-20 11:03:12',4,4),
('Un avis de pluie est lancé : une annulation est possible.','20-05-29 16:15:45',4,1),
('Tout va bien, le soleil est prévu pour la journée: aucun risque.','20-06-10 21:56:02',4,3);
