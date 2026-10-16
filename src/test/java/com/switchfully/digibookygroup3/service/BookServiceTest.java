package com.switchfully.digibookygroup3.service;

import com.switchfully.digibookygroup3.api.dtos.AuthorDTO;
import com.switchfully.digibookygroup3.api.dtos.BookDTO;
import com.switchfully.digibookygroup3.api.dtos.BookWithSummaryDTO;
import com.switchfully.digibookygroup3.domain.book.Author;
import com.switchfully.digibookygroup3.domain.book.AuthorRepository;
import com.switchfully.digibookygroup3.domain.book.Book;
import com.switchfully.digibookygroup3.domain.book.BookRepository;
import com.switchfully.digibookygroup3.domain.loan.LoanRepository;
import com.switchfully.digibookygroup3.exception.AuthorNotFoundException;
import com.switchfully.digibookygroup3.exception.BookNotFoundException;
import com.switchfully.digibookygroup3.mapper.AuthorMapper;
import com.switchfully.digibookygroup3.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceTest {
    private BookService bookService;
    private BookRepository bookRepositoryMock;
    private AuthorRepository authorRepositoryMock;
    private LoanRepository loanRepositoryMock;
    private List<Author> authors;
    private List<Book> books;

    @BeforeEach
    void setup() {
        bookRepositoryMock = mock(BookRepository.class);
        authorRepositoryMock = mock(AuthorRepository.class);
        bookService = new BookService(bookRepositoryMock, authorRepositoryMock,
                new BookMapper(new AuthorRepository(), new LoanRepository()), new AuthorMapper(), loanRepositoryMock);
        addAuthorsAndBooks();
    }

    @Test
    void getAll_givenFilledRepositories_whenGetAllBooks_thenReturnsAllBookDTOs() {
        String firstAuthorId = authors.get(0).getId();
        String secondAuthorId = authors.get(1).getId();

        when(bookRepositoryMock.getAllBooks())
                .thenReturn(books);
        when(authorRepositoryMock.getById(firstAuthorId))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(secondAuthorId))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> bookDTOS = bookService.getAllBooks();

        verify(bookRepositoryMock).getAllBooks();
        verify(authorRepositoryMock).getById(firstAuthorId);
        verify(authorRepositoryMock).getById(secondAuthorId);

        assertThat(bookDTOS).isNotEmpty().hasSize(2);
        assertThat(bookDTOS.get(0).getId()).isNotEmpty();
        assertThat(bookDTOS).extracting(BookDTO::getIsbn).containsOnly("ISBN1", "ISBN2");
        assertThat(bookDTOS)
                .extracting(BookDTO::getAuthorDTO)
                .extracting(AuthorDTO::firstname)
                .containsOnly("a1_firstname", "a2_firstname");
    }

    @Test
    void getAll_givenBooksWithInvalidAuthorId_whenGetAllBooks_thenThrowsAuthorNotFoundException() {
        books = new LinkedList<>(List.of(new Book("ISBN1", "title1", "invalidId", "Summary")));

        when(bookRepositoryMock.getAllBooks())
                .thenReturn(books);

        assertThatThrownBy(() -> bookService.getAllBooks())
                .isInstanceOf(AuthorNotFoundException.class)
                .hasMessage("Author not found with id invalidId");
    }

    @Test
    void getListOfBooks_WhenNonExistingAuthorIsPassedOnToRequestBooksByAuthor() {
        List<BookDTO> result = bookService.searchBooksByAuthor("a1*");

        assertThat(result).isEmpty();

    }

    @Test
    void getBookById_givenAValidIdOfBookWithSummary_whenGetBookById_thenReturnBookDtoWithAllDetailsOfTheBook() {
        Author author = authors.get(0);
        Book book = books.get(0);

        when(bookRepositoryMock.getBydId(book.getId()))
                .thenReturn(Optional.of(book));
        when(authorRepositoryMock.getById(author.getId()))
                .thenReturn(Optional.of(author));

        BookWithSummaryDTO bookWithSummaryDTO = bookService.getBookById(book.getId());

        verify(bookRepositoryMock).getBydId(book.getId());
        verify(authorRepositoryMock).getById(author.getId());

        assertThat(bookWithSummaryDTO).isNotNull();
        assertThat(bookWithSummaryDTO.getSummary()).isEqualTo(book.getSummary());
        assertThat(bookWithSummaryDTO.getId()).isEqualTo(book.getId());
        assertThat(bookWithSummaryDTO.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookWithSummaryDTO.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(bookWithSummaryDTO.getAuthorDTO())
                .isEqualTo(new AuthorDTO(author.getId(), author.getFirstname(), author.getLastname()));
    }

    @Test
    void getBookById_givenAnInvalidId_whenCalled_thenReturnBookNotFoundException() {
        assertThatThrownBy(() -> bookService.getBookById("InvalidBookId"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found with id InvalidBookId");
    }

    private void addAuthorsAndBooks() {
        authors = new LinkedList<>(List.of(new Author("a1_firstname", "a1_lastname"),
                new Author("a2_firstname", "a2_lastname")));
        books = new LinkedList<>(List.of(new Book("ISBN1", "title1", authors.get(0).getId(), "Summary1"),
                new Book("ISBN2", "title2", authors.get(1).getId(), "Summary2")));
    }

    @Test
    void getBookByISBNShouldReturnAListOfBooksWithTheExactISBNNumber() {
        Author author = authors.get(0);
        Book book = books.get(0);

        when(bookRepositoryMock.getBooksByISBNNumber("ISBN1"))
                .thenReturn(List.of(book));
        when(authorRepositoryMock.getById(author.getId()))
                .thenReturn(Optional.of(author));

        List<BookDTO> filteredList = bookService.getBookByISBN("ISBN1");

        assertThat(filteredList).hasSize(1);
    }

    @Test
    void getBookByISBNShouldReturnAListOfBooksContainingThatISBNNumber() {

        when(bookRepositoryMock.getBooksByISBNNumber("IS*"))
                .thenReturn(books);
        when(authorRepositoryMock.getById(authors.get(0).getId()))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(authors.get(1).getId()))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> filteredList = bookService.getBookByISBN("IS*");

        assertThat(filteredList)
                .hasSize(2)
                .extracting(BookDTO::getIsbn)
                .containsOnly("ISBN1", "ISBN2");
    }

    @Test
    void getBookByISBNShouldReturnAListOfBooksContainingTheEndThatISBNNumber() {

        when(bookRepositoryMock.getBooksByISBNNumber("*BN*"))
                .thenReturn(books);
        when(authorRepositoryMock.getById(authors.get(0).getId()))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(authors.get(1).getId()))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> filteredList = bookService.getBookByISBN("*BN*");

        assertThat(filteredList)
                .hasSize(2)
                .extracting(BookDTO::getIsbn)
                .containsOnly("ISBN1", "ISBN2");
    }

    @Test
    void getBookByISBNShouldReturnAnEmptyList() {
        List<BookDTO> filteredList = bookService.getBookByISBN("IS*");

        assertThat(filteredList).isEmpty();
    }

    @Test
    void getBookByTitleShouldReturnAListOfBooksWithTheExactTitle() {
        Author author = authors.get(0);
        Book book = books.get(0);

        when(bookRepositoryMock.getBooksByTitle("title1"))
                .thenReturn(List.of(book));
        when(authorRepositoryMock.getById(author.getId()))
                .thenReturn(Optional.of(author));

        List<BookDTO> filteredList = bookService.getBookByTitle("title1");

        assertThat(filteredList).hasSize(1);
    }

    @Test
    void getBookByTitleShouldReturnAListOfBooksContainingThatTitle() {

        when(bookRepositoryMock.getBooksByTitle("ti*"))
                .thenReturn(books);
        when(authorRepositoryMock.getById(authors.get(0).getId()))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(authors.get(1).getId()))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> filteredList = bookService.getBookByTitle("ti*");

        assertThat(filteredList)
                .hasSize(2)
                .extracting(BookDTO::getTitle)
                .containsOnly("title1", "title2");
    }

    @Test
    void getBookByTitleShouldReturnAListOfBooksContainingTheEndThatTitle() {

        when(bookRepositoryMock.getBooksByTitle("*TL*"))
                .thenReturn(books);
        when(authorRepositoryMock.getById(authors.get(0).getId()))
                .thenReturn(Optional.of(authors.get(0)));
        when(authorRepositoryMock.getById(authors.get(1).getId()))
                .thenReturn(Optional.of(authors.get(1)));

        List<BookDTO> filteredList = bookService.getBookByTitle("*TL*");

        assertThat(filteredList)
                .hasSize(2)
                .extracting(BookDTO::getTitle)
                .containsOnly("title1", "title2");
    }

    @Test
    void getBookByTitleShouldReturnAnEmptyList() {
        List<BookDTO> filteredList = bookService.getBookByISBN("ti*");

        assertThat(filteredList).isEmpty();
    }

    @Test
    void getListOfBooks_WhenExistingAuthorIsPassedOnToRequestBooksByAuthor() {
        List<Author> authorList = new LinkedList<>(List.of(new Author("a1_firstname", "a1_lastname"),
                new Author("a2_firstname", "a2_lastname")));
        String firstAuthorId = authorList.get(0).getId();
        String secondAuthorId = authorList.get(1).getId();

        List<Book> bookList = new LinkedList<>(List.of(new Book("ISBN1", "title1", firstAuthorId, "summary"),
                new Book("ISBN2", "title2", secondAuthorId, "summary")));

        Set<String> expectedAuthorIds = Set.of(firstAuthorId);

        when(authorRepositoryMock.getAuthorsByName("a1*"))
                .thenReturn(List.of(authorList.get(0)));
        when(authorRepositoryMock.getById(firstAuthorId))
                .thenReturn(Optional.of(authorList.get(0)));
        when(bookRepositoryMock.getBooksByAuthorIds(expectedAuthorIds))
                .thenReturn(Collections.singletonList(bookList.get(0)));

        System.out.println(firstAuthorId);
        System.out.println(bookList.get(0).getAuthorId());

        List<BookDTO> result = bookService.searchBooksByAuthor("a1*");

        verify(bookRepositoryMock).getBooksByAuthorIds(expectedAuthorIds);
        assertThat(result)
                .isNotEmpty()
                .extracting(BookDTO::getAuthorDTO)
                .extracting(AuthorDTO::firstname)
                .contains("a1_firstname");
    }

    @Test
    void deleteBook_givenAnExistingBook_whenDeleteBook_thenReturnsOK() {
        addAuthorsAndBooks();
        Book book = books.get(0);
        when(bookRepositoryMock.getBydId(book.getId()))
                .thenReturn(Optional.of(book));
        doAnswer(invocation ->  {
            book.softDelete();
            return null;
        }).when(bookRepositoryMock).deleteBook(book);

        bookService.deleteBook(book.getId());

        assertThat(book.isDeleted()).isTrue();
    }

    @Test
    void deleteBook_givenAnExistingButDeletedBook_whenDeleteBook_thenThrowsBookNotFoundException() {
        addAuthorsAndBooks();
        Book book = books.get(0);
        book.softDelete();

        when(bookRepositoryMock.getBydId(book.getId()))
                .thenReturn(Optional.of(book));

        String bookId = book.getId();
        assertThatThrownBy(()-> bookService.deleteBook(bookId))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found with id " + book.getId());
    }

    @Test
    void deleteBook_givenNonExistingBook_whenDeleteBook_thenThrowsBookNotFoundException() {
        assertThatThrownBy(()-> bookService.deleteBook("invalidId"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found with id invalidId");
    }

    @Test
    void restoreBook_givenAnExistingButDeletedBook_whenRestoreBook_thenReturnsOK() {
        addAuthorsAndBooks();
        Book book = books.get(0);
        book.softDelete();

        when(bookRepositoryMock.getBydId(book.getId()))
                .thenReturn(Optional.of(book));
        doAnswer(invocation ->  {
            book.restoreBook();
            return null;
        }).when(bookRepositoryMock).restoreBook(book);

        bookService.restoreBook(book.getId());

        assertThat(book.isDeleted()).isFalse();
    }

    @Test
    void restoreBook_givenNonExistingBook_whenRestoreBook_thenThrowsBookNotFoundException() {
        assertThatThrownBy(()-> bookService.restoreBook("invalidId"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Book not found with id invalidId");
    }
}
