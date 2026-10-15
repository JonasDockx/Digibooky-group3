package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
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
}
