drop database library;
create database library;
use library;
create table books(idBook int auto_increment primary key, bookName VARCHAR(200) unique, publishingDate date, isTaken boolean default false);
create table genre(idGenre int auto_increment primary key, genreName VARCHAR(200) unique);
create table author(idAuthor int auto_increment primary key, authorName VARCHAR(200) unique);
create table authorsOfBooks(bookId int, authorId int, primary key(bookId, authorId),
	foreign key (bookId) references books(idBook), foreign key (authorId) references author(idAuthor));
create table genresOfBooks(bookId int, genreId int, primary key(bookId, genreId),
	foreign key (bookId) references books(idBook), foreign key (genreId) references genre(idGenre));
    
insert into genre (genreName) value('fantastic');    
insert into genre (genreName) values('science');    
insert into genre (genreName) values('science-fantastic');    
insert into genre (genreName) values('adventure');    
insert into genre (genreName) values('roman');
    
insert into author (authorName) values('Bloh');    
insert into author (authorName) values('Romanchick');    
insert into author (authorName) values('Blinov');    
insert into author (authorName) values('Eckel');    
insert into author (authorName) values('Perumov');    
insert into author (authorName) values('Lukhyanenko');    

insert into books (bookName, publishingDate, isTaken) values('Night watch', '2010.02.20', false);
insert into books (bookName, publishingDate, isTaken) values('Effective java', '2010.02.21', false);
insert into books (bookName, publishingDate, isTaken) values('Java enterprise development', '2000.02.20', false);
insert into books (bookName, publishingDate, isTaken) values('Programm Eckel', '21.02.2000', false);
insert into books (bookName, publishingDate, isTaken) values('Do not time for dragon', '1998.02.02', false);

insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Night watch')
	, (Select idAuthor from author where authorName = 'Lukhyanenko'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Effective java')
	, (Select idAuthor from author where authorName = 'Bloh'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Java enterprise development')
	, (Select idAuthor from author where authorName = 'Blinov'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Java enterprise development')
	, (Select idAuthor from author where authorName = 'Romanchick'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Programm Eckel')
	, (Select idAuthor from author where authorName = 'Eckel'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Do not time for dragon')
	, (Select idAuthor from author where authorName = 'Perumov'));
insert into authorsOfBooks (bookId, authorId) values((Select idBook from books where bookName = 'Do not time for dragon')
	, (Select idAuthor from author where authorName = 'Lukhyanenko'));
    
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Night watch')
	, (Select idGenre from genre where genreName = 'fantastic'));
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Night watch')
	, (Select idGenre from genre where genreName = 'roman'));
 insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Night watch')
	, (Select idGenre from genre where genreName = 'adventure'));
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Effective java')
	, (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Java enterprise development')
	, (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Programm Eckel')
	, (Select idGenre from genre where genreName = 'science'));
insert into genresOfBooks (bookId, genreId) values((Select idBook from books where bookName = 'Do not time for dragon')
	, (Select idGenre from genre where genreName = 'fantastic'));

