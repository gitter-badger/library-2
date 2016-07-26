package sigxcpuyaru.library.service;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import sigxcpuyaru.library.entity.Book;
import sigxcpuyaru.library.entity.User;
import sigxcpuyaru.library.dao.BookDAO;
import sigxcpuyaru.library.dao.UserDAO;

public class BookBatcher {

    private static final Logger log = LogManager.getLogger(BookBatcher.class);

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserDAO userDAO;

    private BatchingMode batchingMode = BatchingMode.BY_AUTHOR;
    private int batchSize = 5;
    private Book lastRetrievedBook = null;
    private boolean tableConsumed = false;

    public enum BatchingMode {
        BY_AUTHOR, BY_TITLE
    }

    public BookBatcher(BookDAO bookDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    public void setMode(BatchingMode newMode) {
        if (newMode != batchingMode) {
            batchingMode = newMode;
            reset();
        }
    }

    public void reset() {
        lastRetrievedBook = null;
        tableConsumed = false;
        log.trace("reset()");
    }

    public boolean isTableConsumed() {
        return tableConsumed;
    }

    public void setBatchSize(int newBatchSize) {
        if (newBatchSize > 0 && newBatchSize != batchSize)
            batchSize = newBatchSize;
    }

    public List<Book> getNextBatchOfBooks() {        
        if (!tableConsumed) {
            List<Book> result = getNextBooksWithRegardToBatchingMode();
            int size = result.size();
            if (size < batchSize)
                tableConsumed = true;
            lastRetrievedBook = result.get(size - 1);
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private List<Book> getNextBooksWithRegardToBatchingMode() {
        if (batchingMode == BatchingMode.BY_AUTHOR)
            return bookDAO.getNextBooksSortedByAuthor(batchSize, lastRetrievedBook);
        else
            return bookDAO.getNextBooksSortedByTitle(batchSize, lastRetrievedBook);
    }
}
