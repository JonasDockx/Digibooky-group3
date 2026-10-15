package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private Map<String, Book> books;

    public BookRepository() {
        this.books = new ConcurrentHashMap<>();
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books.values());
    }

    public List<Book> getBooksByAuthorIds(Set<String> authorIds) {
        return books.values().stream()
                .filter(book -> authorIds.contains(book.getAuthorId()))
                .toList();
    }
}
