create table if not exists persistent_logins (
  username varchar(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
);

INSERT INTO activity_type (name,met) VALUES
('Cycling',7),
('Running',7.8),
('Swimming',5.5);

INSERT INTO level (name, place, maximum_threshold, ratio_points) VALUES
('Beginner',1,50,1),
('Active',2,120,0.8),
('Sporstman',3,200,0.5),
('Athlete',4,350,0.4),
('Hero',5,600,0.3);

INSERT INTO sports_man (first_name,last_name,description,date_of_birth,weight,points,email,password, fk_level,blocked) VALUES
('Laurent','Weber','Chevelu Sportif','1990-05-29',79,35,'lo@gmail.com','$2y$10$e/M4T66Ioz3rZ0fltX7qpeLzonDbP/sD60zehJ77t0SoJUCF.WGMa',1,false),
('Michael','Atlas','Photograhe en tout genre','1988-04-01',90,32,'mike@gmail.com','$2y$10$0w8iBx3F0.9N9x5eGgy73eqhkDRcKXGnoZQyqvFZ9KC0XEeyHRi0K',1,false),
('Geoffrey','Moyens','Militaire et Patate Mariée','1983-02-02',95,40,'geof@gmail.com','$2y$10$4g0uGcj7IjljDWoCY5e8X.T2GbVP2e/RPs65.MbUvYECccKlJiQqW',1,true),
('Romain','Monsterlet','Animateur et Flûtiste','1990-12-06',83,84,'ro@gmail.com','$2y$10$FbazGLhM/vvjhS8ykF5neu58q9cdQB02b1DVVdPfHY47S6zoAw3US',1,false),
('Baptiste','Marck','Orval et Web','1990-10-29',70,43,'ba@gmail.com','$2y$10$NbmUdZTlCUZxXtWCmMxhjOCEbEXa1aASSMueCoQYcfNmjxtPhU8m6',1,false),
('Troy','Devrieze','Crêtu Blondinet','1990-07-29',65,36,'troy@gmail.com','$2y$10$.Xp0.U7Xym8UzPNIKTO/Ted7AZckkHRMWJ4tybWEgZVRXD.5WGkm2',1,false);

--Old Events
INSERT INTO activity (name, fk_activity, fk_creator,duration,open,over,fk_minimum_level, fk_maximum_level, planned_to) VALUES
('Initiation Crawl',3,1,90,true,true,1,1,'2020-08-10 14:00:00' ),
('Namur-Ottiginies',1,2,60,true,true,2,4,'2020-07-15 09:30:00'),
('Cross au Bois de Malonne',2,1,180,true,true,1,2,'2020-07-04 08:00:00'),
('Descente de la Meuse',3,3,45,true,true,1,3,'2020-06-25 17:30:00');

--Current Events
INSERT INTO activity (name, fk_activity, fk_creator,duration,open,over,fk_minimum_level, fk_maximum_level, planned_to) VALUES
('Brasse Bien Grasse',3,1,90,false,false,2,3,'2020-08-10 14:00:00' ),
('Footing Mosan',2,2,60,true,false,1,2,'2020-07-15 09:30:00'),
('Vélo en Folie',1,3,180,true,false,1,1,'2020-07-04 08:00:00'),
('Run & Beer',2,1,45,true,false,1,2,'2020-06-25 17:30:00');

INSERT INTO activity_registered(registered_activities_id, registered_id) VALUES
(1,1),
(1,4),
(1,5),
(1,6),
(2,2),
(2,3),
(3,1),
(3,4),
(4,2),
(4,3),
(4,4),
(4,5),
(4,6),
(8,6),
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
(12,120,4,2),
(15,150,4,3),
(14,140,4,4),
(11,110,4,5),
(10,100,4,6);

INSERT INTO sports_man_contacts (sports_man_id,contacts_id) VALUES
(2,1),
(2,3),
(1,3),
(5,1)
;

INSERT INTO role (name) VALUES
('ROLE_SIMPLY'),
('ROLE_CONFIRMED'),
('ROLE_ADMINISTRATOR');

INSERT INTO user_role (users_id,roles_id) VALUES
(1,3),
(1,2),
(2,2),
(3,2),
(4,1),
(5,1),
(6,1);