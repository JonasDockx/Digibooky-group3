package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
        return List.copyOf(books.values());
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public Optional<Book> getBydId(String id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> getBooksByISBNNumber(String isbn) {
        String regex = "^" + isbn.trim().replace("*", "\\d*") + "$";

        return books.values().stream()
                .filter(book -> book.getIsbn().matches(regex))
                .toList();
    }

    public List<Book> getBooksByTitle(String title) {
        String regex = "(?i)^" + title.trim().replace("*", "\\d*") + "$";

        return books.values().stream()
                .filter(book -> book.getTitle().matches(regex))
    public List<Book> getBooksByAuthorIds(Set<String> authorIds) {
        return books.values().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
