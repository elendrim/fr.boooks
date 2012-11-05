INSERT INTO GENRE (GENRE_ID, LI_GENRE) VALUES (1,'Science fiction');
INSERT INTO GENRE (GENRE_ID, LI_GENRE) VALUES (2,'Policier');
INSERT INTO GENRE (GENRE_ID, LI_GENRE) VALUES (3,'Amour');
INSERT INTO GENRE (GENRE_ID, LI_GENRE) VALUES (4,'Fantastique');
INSERT INTO GENRE (GENRE_ID, LI_GENRE) VALUES (5,'Heroic Fantasy');

INSERT INTO TYPE (TYPE_ID, LI_TYPE) VALUES (1,'Nouvelle');
INSERT INTO TYPE (TYPE_ID, LI_TYPE) VALUES (2,'Roman');
INSERT INTO TYPE (TYPE_ID, LI_TYPE) VALUES (3,'Essai');
INSERT INTO TYPE (TYPE_ID, LI_TYPE) VALUES (4,'Thèse');
INSERT INTO TYPE (TYPE_ID, LI_TYPE) VALUES (5,'Manga');


INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (1,1,'0d05845a31cfa01390dee7a91fb7c8ee2590fcee7875fa9a80583e307f10e4f7','1998-12-12','Grégory','OLIVER','MALE','gregory@boooks.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (3,1,'04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb','2001-03-14','Prénom','Nom','MALE','user@boooks.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (4,1,'8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','2015-11-16','Admin','ADMIN','MALE','admin@boooks.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (10,1,'4d0687a8ce384ce2f6742deb87d634c470167438548b5c20277fccd9b6873210','1998-02-01','gnou','gnou','OTHER','gnou');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (11,1,'4d0687a8ce384ce2f6742deb87d634c470167438548b5c20277fccd9b6873210','1889-02-11','gnou2','gnou2','OTHER','gnou2');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (12,1,'7e40b23e521be18f00737085c99ba53ec3564f3fa344e2814fd743818c68f413','1887-02-11','gnou3','gnou3','MALE','gnou3');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (16,0,'9b7ecc6eeb83abf9ade10fe38865df4499be3568dcc507ae2ec3b44989cb0093','1998-03-31','zz','dd','FEMALE','aa@dd.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (17,0,'9b7ecc6eeb83abf9ade10fe38865df4499be3568dcc507ae2ec3b44989cb0093','1998-03-31','dd','gg','FEMALE','email@meil.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (18,0,'9b7ecc6eeb83abf9ade10fe38865df4499be3568dcc507ae2ec3b44989cb0093','1998-02-28','jeargoe','heagkj','FEMALE','george@boooks.fr');
INSERT INTO USER (USER_ID, ACTIVE, PASSWORD, BIRTHDATE, FIRSTNAME, LASTNAME, SEX, EMAIL) VALUES (19,1,'244210e48437b6556980a70249a99369934a352429034cef9d7bd253b3bf2c01','1985-05-12','Grégory','OLIVER','MALE','gregory.oliver@boooks.fr');


INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('ADMIN',1);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',1);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',3);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('ADMIN',4);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',10);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',11);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',12);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',16);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',17);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',18);
INSERT INTO SECURITY_ROLE (ROLE_NAME, USER_ID) VALUES ('USER',19);



INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (5,NULL,NULL,0,'2012-10-26 14:57:09','Résumé !!','Le début d''un nouveau Monde',5,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (6,NULL,NULL,0,'2012-10-26 15:09:09','Resumé !!','Publi2',5,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (8,NULL,NULL,0,'2012-10-29 16:52:21','Résuméé !! ho yeah','Titte 4',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (9,NULL,NULL,0,'2012-10-29 16:53:05','Resumé','Titre5',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (10,NULL,NULL,0,'2012-10-29 16:53:30','Resumé très simplisime','encore au autre 6',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (11,NULL,NULL,0,'2012-10-29 16:53:46','Resum','Blagues a gogo',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (12,NULL,NULL,0,'2012-10-29 16:55:09','Resumé des tréphon de nom d''une pipe en bois !','Encore un pour la ligne :)',5,1,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (13,NULL,NULL,0,'2012-10-30 10:51:08','Gnou ?','Titre Gregory',5,5,1);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (17,NULL,NULL,0,'2012-10-31 13:59:39','C''est l''histoire d''un nouveau nouveau titre !','New new titre',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (18,NULL,NULL,0,'2012-10-31 14:03:24','ddd','Encore un super nouveau titre de la mort !',2,5,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (19,NULL,NULL,0,'2012-11-02 14:03:26','essai','Grégory title',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (20,NULL,NULL,0,'2012-11-02 14:04:35','essai2','Grégory 2 le retour',3,3,3);
INSERT INTO BOOK (BOOK_ID, DESCRIPTION, KEYWORDS, NB_PAGE, PUBLISH_DATE, RESUME, TITLE, GENRE_ID, TYPE_ID, USER_ID) VALUES (21,NULL,NULL,0,'2012-11-02 15:00:09','Le dernier !','last one',3,3,3);


INSERT INTO AUTHOR (AUTHOR_ID, NAME) VALUES (1,'Grégory OLIVER');
INSERT INTO AUTHOR (AUTHOR_ID, NAME) VALUES (7,'John doe');
INSERT INTO AUTHOR (AUTHOR_ID, NAME) VALUES (2,'John Who');
INSERT INTO AUTHOR (AUTHOR_ID, NAME) VALUES (6,'Prénom Nom');
INSERT INTO AUTHOR (AUTHOR_ID, NAME) VALUES (8,'Sans auteur');


INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (5,1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (8,2);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (13,1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (13,2);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (17,1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (17,6);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (18,1);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (18,6);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (18,7);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (19,8);
INSERT INTO BOOKS_AUTHORS (BOOK_ID, AUTHOR_ID) VALUES (21,6);

INSERT INTO TEMP_KEY (EMAIL, TEMP_KEY) VALUES ('aa@dd.fr','c3d5e072-7e06-42c2-8220-f69c9bc6967d');
INSERT INTO TEMP_KEY (EMAIL, TEMP_KEY) VALUES ('email@meil.fr','13eb5c39-41fa-4382-8dc6-5a471a1fe56f');
INSERT INTO TEMP_KEY (EMAIL, TEMP_KEY) VALUES ('george@boooks.fr','b738bf22-e38d-431c-b7c0-7e6d1fc1d907');
INSERT INTO TEMP_KEY (EMAIL, TEMP_KEY) VALUES ('gnou2','ca597a7f-33ad-45d8-920d-9d581ff407aa');
INSERT INTO TEMP_KEY (EMAIL, TEMP_KEY) VALUES ('gnou3','0efee92f-d28a-4e1f-a773-55f3ca86a28b');