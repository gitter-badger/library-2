package sigxcpuyaru.library.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import sigxcpuyaru.library.service.Service;
import sigxcpuyaru.library.service.BookBatcher;
import sigxcpuyaru.library.entity.Book;
import sigxcpuyaru.library.entity.User;

@Controller
public class LibraryController {

    private static final Logger log = LogManager.getLogger(LibraryController.class);

    @Autowired
    private Service service;

    @Autowired
    private BookBatcher bookBatcher;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String books() {
        log.trace("books()");
        bookBatcher.reset();
        return "books";
    }
    //
    // @RequestMapping("*")
    // public String books(HttpServletRequest request) {
    //     log.trace(request.getServletPath());
    //     return "books";
    // }

    @RequestMapping(value="/ajax/moreBooks", method=RequestMethod.GET)
    public @ResponseBody List<Book> moreBooks() {
        log.trace("moreBooks()");
        return bookBatcher.getNextBatchOfBooks();
    }

    // @RequestMapping(value="/ajax/borrowBook", method=RequestMethod.POST)
    // public ResponseEntity<String> borrowBook(@RequestBody String isbn) {
    //     log.trace("borrowBook() :: isbn = " + isbn);
    //     service.borrowBook(isbn);
    //     return new ResponseEntity<String>(HttpStatus.OK);
    // }

    @RequestMapping(value="/ajax/returnBook", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> returnBook(@RequestBody String isbn) {
        log.trace("returnBook() :: isbn = " + isbn);
        service.returnBook(isbn);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    // @RequestMapping(value="/ajax/deleteBook", method=RequestMethod.POST)
    // public ResponseEntity<String> deleteBook(@RequestBody String isbn) {
    //     service.deleteBook(isbn);
    //     return new ResponseEntity<String>(HttpStatus.OK);
    // }

    // @RequestMapping(value="/ajax/deleteUser", method=RequestMethod.POST)
    // public @ResponseBody void deleteUser(@RequestBody String isbn) {
    //     service.deleteUser(id);
    // }
    //
    // @RequestMapping(value="/ajax/changeBatchingMode/{newMode}", method=RequestMethod.GET)
    // public @ResponseBody void changeBatchingMode(@PathVariable BookBatcher.BatchingMode newMode) {
    //     bookBatcher.setMode(newMode);
    // }

    // @RequestMapping(value="/ajax/deleteUser/{id}", method=RequestMethod.POST)
    // public @ResponseBody List<Book> addUser(@PathVariable int id) {
}
