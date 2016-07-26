package sigxcpuyaru.library.service;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import sigxcpuyaru.library.entity.Book;
import sigxcpuyaru.library.entity.User;
import sigxcpuyaru.library.dao.BookDAO;
import sigxcpuyaru.library.dao.UserDAO;

public class Service {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserDAO userDAO;

    public Service(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    public void addBook(Book book) {
        bookDAO.insertBook(book);
    }

    public void addUser(User user) {
        userDAO.insertUser(user);
    }

    public void borrowBook(String isbn, User user) {
        Book b = bookDAO.getBook(isbn);
        b.setBorrower(user);
        bookDAO.updateBook(b);
    }

    public void returnBook(String isbn) {
        Book b = bookDAO.getBook(isbn);
        b.setBorrower(null);
        bookDAO.updateBook(b);
    }

    public void deleteBook(String isbn) {
        bookDAO.deleteBook(isbn);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    // public List<Book> getNextBooksSortedByAuthor(int numOfBooks) {
    //     if (!tableConsumedByAuthor) {
    //         List<Book> result = bookDAO.getNextBooksSortedByAuthor(numOfBooks, lastRetrievedBookByAuthor);
    //         int size = result.size();
    //         if (size < numOfBooks)
    //             tableConsumedByAuthor = true;
    //         lastRetrievedBookByAuthor = result.get(size - 1);
    //         return result;
    //     } else {
    //         return Collections.emptyList();
    //     }
    // }
    //
    // public List<Book> getNextBooksSortedByTitle(int numOfBooks) {
    //     if (!tableConsumedByTitle) {
    //         List<Book> result = bookDAO.getNextBooksSortedByTitle(numOfBooks, lastRetrievedBookByTitle);
    //         int size = result.size();
    //         if (size < numOfBooks)
    //             tableConsumedByTitle = true;
    //         lastRetrievedBookByTitle = result.get(size - 1);
    //         return result;
    //     } else {
    //         return Collections.emptyList();
    //     }
    // }
}
