package sigxcpuyaru.library.entity;

public class Book {

    private int id;
    private String isbn;
    private String author;
    private String title;
    private User borrower;

    public Book(int id, String isbn, String author, String title, User borrower) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.borrower = borrower;
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }
}
