create database library;
use library;
CREATE TABLE books (
                     idBook INT AUTO_INCREMENT PRIMARY KEY,
                     bookName VARCHAR(200) UNIQUE,
                     publishingDate DATE,
                     count INT(11) DEFAULT NULL,
                     isTaken BOOLEAN DEFAULT FALSE
);
CREATE TABLE genre (
                     idGenre INT AUTO_INCREMENT PRIMARY KEY,
                     genreName VARCHAR(200) UNIQUE
);
CREATE TABLE author (
                      idAuthor INT AUTO_INCREMENT PRIMARY KEY,
                      authorName VARCHAR(200) UNIQUE
);
CREATE TABLE authorsOfBooks (
                              idAuthorBook INT AUTO_INCREMENT PRIMARY KEY,
                              bookId INT,
                              authorId INT,
                              UNIQUE (bookId , authorId),
                              FOREIGN KEY (bookId)
                                REFERENCES books (idBook),
                              FOREIGN KEY (authorId)
                                REFERENCES author (idAuthor)
);
CREATE TABLE genresOfBooks (
                             idGenreBook INT AUTO_INCREMENT PRIMARY KEY,
                             bookId INT,
                             genreId INT,
                             UNIQUE (bookId , genreId),
                             FOREIGN KEY (bookId)
                               REFERENCES books (idBook),
                             FOREIGN KEY (genreId)
                               REFERENCES genre (idGenre)
);
CREATE TABLE readers (
                       idReader INT(11) NOT NULL AUTO_INCREMENT,
                       readerName VARCHAR(20) DEFAULT NULL,
                       passw VARCHAR(50) DEFAULT NULL,
                       accountNonExpired TINYINT(1) DEFAULT NULL,
                       accountNonLocked TINYINT(1) DEFAULT NULL,
                       credentialsNonExpired TINYINT(1) DEFAULT NULL,
                       enabled TINYINT(1) DEFAULT NULL,
                       PRIMARY KEY (idReader),
                       UNIQUE KEY readerName (readerName)
);

CREATE TABLE bookofreaders (
                             id INT(11) NOT NULL AUTO_INCREMENT,
                             readerId INT(11) DEFAULT NULL,
                             bookId INT(11) DEFAULT NULL,
                             PRIMARY KEY (id),
                             UNIQUE KEY readerId (readerId , bookId),
                             KEY bookId (bookId),
                             CONSTRAINT bookofreaders_ibfk_1 FOREIGN KEY (readerId)
                               REFERENCES readers (idreader),
                             CONSTRAINT bookofreaders_ibfk_2 FOREIGN KEY (bookId)
                               REFERENCES books (idbook)
);

CREATE TABLE roles (
                     idRole INT(11) NOT NULL AUTO_INCREMENT,
                     roleName VARCHAR(20) DEFAULT NULL,
                     PRIMARY KEY (idRole),
                     UNIQUE KEY roleName (roleName)
);

CREATE TABLE rolesreaders (
                            id INT(11) NOT NULL AUTO_INCREMENT,
                            readerId INT(11) DEFAULT NULL,
                            roleId INT(11) DEFAULT NULL,
                            PRIMARY KEY (id),
                            UNIQUE KEY readerId (readerId , roleId),
                            KEY roleId (roleId),
                            CONSTRAINT rolesreaders_ibfk_1 FOREIGN KEY (readerId)
                              REFERENCES readers (idreader),
                            CONSTRAINT rolesreaders_ibfk_2 FOREIGN KEY (roleId)
                              REFERENCES roles (idrole)
);

insert into genre (genreName) value ('fantastic');
insert into genre (genreName) values ('science');
insert into genre (genreName) values ('science-fantastic');
insert into genre (genreName) values ('adventure');
insert into genre (genreName) values ('roman');

insert into author (authorName) values ('Bloh');
insert into author (authorName) values ('Romanchick');
insert into author (authorName) values ('Blinov');
insert into author (authorName) values ('Eckel');
insert into author (authorName) values ('Perumov');
insert into author (authorName) values ('Lukhyanenko');

insert into books (bookName, publishingDate, count, isTaken) values ('Night watch', '2010.02.20', 3, false);
insert into books (bookName, publishingDate, count, isTaken) values ('Effective java', '2010.02.21', 3, false);
insert into books (bookName, publishingDate, count, isTaken) values ('Java enterprise development', '2000.02.20', 3, false);
insert into books (bookName, publishingDate, count, isTaken) values ('Programm Eckel', '21.02.2000', 3, false);
insert into books (bookName, publishingDate, count, isTaken) values ('Do not time for dragon', '1998.02.02', 3, false);

insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Night watch'),
                                                      (Select idAuthor from author where authorName = 'Lukhyanenko'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Effective java'),
                                                      (Select idAuthor from author where authorName = 'Bloh'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Java enterprise development'),
                                                      (Select idAuthor from author where authorName = 'Blinov'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Java enterprise development'),
                                                      (Select idAuthor from author where authorName = 'Romanchick'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Programm Eckel'),
                                                      (Select idAuthor from author where authorName = 'Eckel'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Do not time for dragon'),
                                                      (Select idAuthor from author where authorName = 'Perumov'));
insert into authorsOfBooks (bookId, authorId) values ((Select idBook from books where bookName = 'Do not time for dragon'),
                                                      (Select idAuthor from author where authorName = 'Lukhyanenko'));

insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Night watch'),
                                                    (Select idGenre from genre where genreName = 'fantastic'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Night watch'),
                                                    (Select idGenre from genre where genreName = 'roman'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Night watch'),
                                                    (Select idGenre from genre where genreName = 'adventure'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Effective java'),
                                                    (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Java enterprise development'),
                                                    (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Programm Eckel'),
                                                    (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values ((Select idBook from books where bookName = 'Do not time for dragon'),
                                                    (Select idGenre from genre where genreName = 'fantastic'));
