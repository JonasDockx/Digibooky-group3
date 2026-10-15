package com.switchfully.digibookygroup3.domain.book;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class AuthorRepository {
    private final Map<String, Author> authors;

    public AuthorRepository() {
        this.authors = new ConcurrentHashMap<>();
    }

    public Optional<Author> getById(String id) {
        return Optional.ofNullable(authors.get(id));
    }

    public Author addAuthor(Author author) {
        authors.put(author.getId(), author);
        return author;
    }

    public List<Author> getAuthorsByName(String name) {
        Pattern pattern = toWildcardPattern(name);
        return authors.values().stream()
                .filter(author -> pattern.matcher(author.getFirstname() + " " + author.getLastname()).find())
                .toList();
    }

    private Pattern toWildcardPattern(String name) {
        String regex = Arrays.stream(name.trim().split("\\*", -1))
                .map(Pattern::quote)
                .collect(Collectors.joining(".*"));
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

}
