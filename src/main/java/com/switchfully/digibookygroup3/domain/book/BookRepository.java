package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final Map<String, Book> books;

    public BookRepository() {
        this.books = new ConcurrentHashMap<>();
    }

    public List<Book> getAllBooks() {
        return filterActiveBooks(List.copyOf(books.values()));
    }

    private List<Book> filterActiveBooks(List<Book> books) {
        return books
                .stream()
                .filter(book -> !book.isDeleted())
                .toList();
    }

    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    public Optional<Book> getBydId(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> getBooksByISBNNumber(String isbn) {
        String regex = "^" + isbn.trim().replace("*", "\\d*") + "$";

        List<Book> allBooks = books.values().stream()
                .filter(book -> book.getIsbn().matches(regex))
                .toList();
        return filterActiveBooks(allBooks);
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return books.values()
                .stream()
                .filter((book -> book.getIsbn().equals(isbn)))
                .findFirst();
    }

    public List<Book> getBooksByTitle(String title) {
        String regex = "(?i)^" + title.trim().replace("*", "\\d*") + "$";

        List<Book> allBooks = books.values().stream()
                .filter(book -> book.getTitle().matches(regex))
                .toList();
        return filterActiveBooks(allBooks);
    }

    public List<Book> getBooksByAuthorIds(Set<String> authorIds) {
        List<Book> allBooks = books.values().stream()
                .filter(book -> authorIds.contains(book.getAuthorId())).toList();

        return filterActiveBooks(allBooks);
    }

    public void deleteBook(Book book) {
        book.softDelete();
    }

    public void restoreBook(Book book) {
        book.restoreBook();
    }
}
