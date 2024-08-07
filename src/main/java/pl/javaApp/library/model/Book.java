package pl.javaApp.library.model;

import java.util.Objects;

public class Book extends Publication{
    public static final String TYPE = "Książka";
    private String author;
    private int pages;
    private String isbn;

    public Book(String title, String publisher, int year, int pages, String author,String isbn) {
        super(title,publisher,year);
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return super.toString() + " autor " + author + " strony " + pages + " isbn " + isbn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        if (!super.equals(o)) return false;
        return pages == book.pages && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, pages, isbn);
    }
}
