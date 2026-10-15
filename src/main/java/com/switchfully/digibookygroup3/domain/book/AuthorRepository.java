package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthorRepository {
    private final Map<String, Author> authors;

    public AuthorRepository() {
        this.authors = new ConcurrentHashMap<>();
    }

    public Author save(Author author) {
        authors.put(author.getId(), author);
        return author;
    }

    public Optional<Author> getById(String id) {
        return Optional.ofNullable(authors.get(id));
    }

}
