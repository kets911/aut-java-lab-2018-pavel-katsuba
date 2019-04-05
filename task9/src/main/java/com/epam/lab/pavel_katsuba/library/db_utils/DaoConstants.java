package com.epam.lab.pavel_katsuba.library.db_utils;

public class DaoConstants {
    public static final String INSERT_AUTHOR = "insert ignore into author (authorName) values(?)";
    public static final String SELECT_AUTHORS = "select * from author";
    public static final String SELECT_AUTHOR_BY_ID = "select * from author where author.idAuthor = (?)";
    public static final String SELECT_AUTHOR_BY_NAME = "select * from author where author.authorName = (?)";
    public static final String UPDATE_AUTHOR = "update author set author.authorName = (?) where authorName = (?)";
    public static final String DELETE_AUTHOR = "delete from author where idAuthor = (?)";

    public static final String INSERT_BOOK = "insert ignore into books (bookName, publishingDate, isTaken) values(?, ?, ?)";
    public static final String SELECT_ALL_BOOKS = "select * from books";
    public static final String SELECT_BOOK_BY_NAME = "select * from books where bookName = (?)";
    public static final String SELECT_BOOK_BY_ID = "select * from books where idBook = (?)";
    public static final String UPDATE_BOOK = "update books set books.bookName = (?), books.publishingDate = (?), " +
            "books.isTaken = (?) where books.idBook = (?)";
    public static final String DELETE_BOOK = "delete from books where books.idBook = (?)";

    public static final String INSERT_GENRE = "insert ignore into genre (genreName) values(?)";
    public static final String SELECT_GENRES = "select * from genre";
    public static final String SELECT_GENRE_BY_ID = "select * from genre where genre.idGenre = (?)";
    public static final String SELECT_GENRE_BY_NAME = "select * from genre where genre.genreName = (?)";
    public static final String UPDATE_GENRE = "update books set genre.genreName = (?) where idGenre = (?)";
    public static final String DELETE_GENRE = "delete from genre where idGenre = (?)";

    public static final String INSERT_AUTHOR_RELATE = "insert ignore into authorsOfBooks values (?, ?)";
    public static final String UPDATE_AUTHOR_RELATE = "update authorsOfBooks set authorsOfBooks.authorId = (?) " +
            "where bookId = (?) and authorId = (?)";
    public static final String DELETE_AUTHOR_RELATE_FOR_BOOK = "delete from authorsOfBooks where bookId = (?)";
    public static final String DELETE_ONE_AUTHOR_RELATE = "delete from authorsOfBooks where bookId = (?) " +
            "and authorId = (?)";
    public static final String SELECT_AUTHORS_FOR_BOOK = "select * from author left join authorsOfBooks " +
            "on author.idAuthor = authorsOfBooks.authorId where authorsOfBooks.bookId = (?)";

    public static final String INSERT_GENRE_RELATE = "insert ignore into genresOfBooks values (?, ?)";
    public static final String UPDATE_GENRE_RELATE = "update genresOfBooks set genresOfBooks.genreId = (?) " +
            "where bookId = (?) and genreId = (?)";
    public static final String DELETE_GENRE_RELATES_FOR_BOOK = "delete from genresOfBooks where bookId = (?)";
    public static final String DELETE_ONE_GENRE_RELATE = "delete from genresOfBooks where bookId = (?) " +
            "and genreId = (?)";
    public static final String SELECT_GENRES_FOR_BOOK = "select * from genre left join genresOfBooks " +
            "on genre.idGenre = genresOfBooks.genreId where genresOfBooks.bookId = (?)";
}
