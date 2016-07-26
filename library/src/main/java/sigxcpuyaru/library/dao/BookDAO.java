package sigxcpuyaru.library.dao;

import java.util.List;

import sigxcpuyaru.library.entity.Book;

public interface BookDAO {

    public Book getBook(int id);
    public Book getBook(String isbn);
    public void insertBook(Book book);
    public void updateBook(Book book);
    public void deleteBook(int id);
    public void deleteBook(String isbn);
    public List<Book> getNextBooksSortedByAuthor(int numOfBooksToRetrieve, Book lastRetrievedBook);
    public List<Book> getNextBooksSortedByTitle(int numOfBooksToRetrieve, Book lastRetrievedBook);
}
