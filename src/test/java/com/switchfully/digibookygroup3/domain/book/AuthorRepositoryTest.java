package com.switchfully.digibookygroup3.domain.book;

import com.switchfully.digibookygroup3.exception.AuthorNotFoundException;
import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AuthorRepositoryTest {
    private AuthorRepository authorRepository;
    private Author rowling;
    private Author tolkien;
    private Author martin;

    @BeforeEach
    void setup() {
        authorRepository = new AuthorRepository();
        rowling = authorRepository.addAuthor(new Author("Joanne", "Rowling"));
        tolkien = authorRepository.addAuthor(new Author("John", "Tolkien"));
        martin = authorRepository.addAuthor(new Author("George", "Martin"));
    }

    @Test
    void getAuthorsByName_givenFirstAndLastName_thenReturnsThatAuthor() {
        assertThat(authorRepository.getAuthorsByName("Joanne Rowling")).containsExactly(rowling);
    }

    @Test
    void getAuthorsByName_givenOnlyFirstName_thenReturnsThatAuthor() {
        assertThat(authorRepository.getAuthorsByName("John")).containsExactly(tolkien);
    }

    @Test
    void getAuthorsByName_givenOnlyLastName_thenReturnsThatAuthor() {
        assertThat(authorRepository.getAuthorsByName("Martin")).containsExactly(martin);
    }

    @Test
    void getAuthorsByName_givenPartOfName_thenReturnsMatchingAuthor() {
        assertThat(authorRepository.getAuthorsByName("Tolk")).containsExactly(tolkien);
    }

    @Test
    void getAuthorsByName_givenWildcardInsideName_thenReturnsMatchingAuthor() {
        assertThat(authorRepository.getAuthorsByName("R*ing")).containsExactly(rowling);
    }

    @Test
    void getAuthorsByName_givenWildcardFirstNameAndFullLastName_thenReturnsMatchingAuthor() {
        assertThat(authorRepository.getAuthorsByName("J* Tolkien")).containsExactly(tolkien);
    }

    @Test
    void getAuthorsByName_givenWildcardMatchingSeveralAuthors_thenReturnsAllOfThem() {
        assertThat(authorRepository.getAuthorsByName("Jo*")).containsExactlyInAnyOrder(rowling, tolkien);
    }

    @Test
    void getAuthorsByName_givenOnlyWildcard_thenReturnsAllAuthors() {
        assertThat(authorRepository.getAuthorsByName("*")).containsExactlyInAnyOrder(rowling, tolkien, martin);
    }

    @Test
    void getAuthorsByName_givenDifferentCase_thenStillMatches() {
        assertThat(authorRepository.getAuthorsByName("rOWLING")).containsExactly(rowling);
    }

    @Test
    void getAuthorsByName_givenUnknownName_thenReturnsEmptyList() {
        assertThatThrownBy(() -> authorRepository.getAuthorsByName("Pratchett"))
                .isInstanceOf(AuthorNotFoundException.class);
    }
}