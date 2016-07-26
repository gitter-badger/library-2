package sigxcpuyaru.library.dao;

import java.util.List;
import java.util.Collections;
import java.sql.ResultSet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import sigxcpuyaru.library.entity.Book;
import sigxcpuyaru.library.entity.User;

public class JdbcTemplateBookDAO implements BookDAO {

    private static final Logger log = LogManager.getLogger(JdbcTemplateBookDAO.class);

    private static final String GET =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "WHERE b.id = ?";
    private static final String GET_BY_ISBN =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "WHERE b.isbn = ?";
    private static final String GET_FIRST_ORDER_BY_AUTHOR =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "ORDER BY b.author, b.isbn " +
        "LIMIT ?";
    private static final String GET_FIRST_ORDER_BY_TITLE =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "ORDER BY b.title, b.isbn " +
        "LIMIT ?";
    private static final String GET_NEXT_ORDER_BY_AUTHOR =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "WHERE (b.author, b.isbn) > (?, ?) " +
        "ORDER BY b.author, b.isbn " +
        "LIMIT ?";
    private static final String GET_NEXT_ORDER_BY_TITLE =
        "SELECT b.id, b.isbn, b.author, b.title, b.borrower_id, u.name AS borrower_name " +
        "FROM books b " +
        "LEFT JOIN users u ON b.borrower_id = u.id " +
        "WHERE (b.title, b.isbn) > (?, ?) " +
        "ORDER BY b.title, b.isbn " +
        "LIMIT ?";
    private static final String INSERT =
        "INSERT INTO books(id, isbn, author, title, borrower_id) " +
        "values(?, ?, ?, ?, ?)";
    private static final String UPDATE =
        "UPDATE books " +
        "SET isbn = ?, author = ?, title = ?, borrower_id = ? " +
        "WHERE id = ?";
    private static final String DELETE = "DELETE FROM books WHERE id = ?";
    private static final String DELETE_BY_ISBN = "DELETE FROM books WHERE isbn = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateBookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getBook(int id) {
        return jdbcTemplate.queryForObject(GET, this::mapBook, id);
    }

    @Override
    public Book getBook(String isbn) {
        return jdbcTemplate.queryForObject(GET_BY_ISBN, this::mapBook, isbn);
    }

    @Override
    public void insertBook(Book book) {
        jdbcTemplate.update(
            INSERT,
            book.getId(), book.getIsbn(), book.getAuthor(), book.getTitle(), book.getBorrower().getId());
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(
            UPDATE,
            book.getIsbn(), book.getAuthor(), book.getTitle(), book.getBorrower().getId(), book.getId());
    }

    @Override
    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public void deleteBook(String isbn) {
        jdbcTemplate.update(DELETE_BY_ISBN, isbn);
    }

    @Override
    public List<Book> getNextBooksSortedByAuthor(int numOfBooksToRetrieve, Book lastRetrievedBook) {
        if (lastRetrievedBook != null) {
            return jdbcTemplate.query(
                GET_NEXT_ORDER_BY_AUTHOR,
                this::mapBook,
                lastRetrievedBook.getAuthor(),
                lastRetrievedBook.getIsbn(),
                numOfBooksToRetrieve);
        } else {
            return jdbcTemplate.query(
                GET_FIRST_ORDER_BY_AUTHOR,
                this::mapBook,
                numOfBooksToRetrieve);
        }
    }

    @Override
    public List<Book> getNextBooksSortedByTitle(int numOfBooksToRetrieve, Book lastRetrievedBook) {
        if (lastRetrievedBook != null) {
            return jdbcTemplate.query(
                GET_NEXT_ORDER_BY_TITLE,
                this::mapBook,
                lastRetrievedBook.getTitle(),
                lastRetrievedBook.getIsbn(),
                numOfBooksToRetrieve);
        } else {
            return jdbcTemplate.query(
                GET_FIRST_ORDER_BY_TITLE,
                this::mapBook,
                numOfBooksToRetrieve);
        }
    }

    private Book mapBook(ResultSet rs, int row) throws SQLException {
        User user = null;
        int borrowerId = rs.getInt("borrower_id");
        if (!rs.wasNull())
            user = new User(rs.getInt("borrower_id"), rs.getString("borrower_name"));

        return new Book(
            rs.getInt("id"),
            rs.getString("isbn"),
            rs.getString("author"),
            rs.getString("title"),
            user);
    }
}
