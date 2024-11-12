package com.project.Book_management_system;

import com.project.Book_management_system.model.Book;
import com.project.Book_management_system.repository.BookRepository;
import com.project.Book_management_system.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublicationYear(2023);
    }

    @Test
    void whenGetAllBooks_thenReturnBookList() {
        // given
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<Book> found = bookService.getAllBooks();

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo(testBook.getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void whenGetBookById_thenReturnBook() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // when
        Optional<Book> found = bookService.getBookById(1L);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo(testBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    // @Test
    // void whenCreateBook_thenReturnSavedBook() {
    //     // given
    //     when(bookRepository.save(any(Book.class))).thenReturn(testBook);

    //     // when
    //     Book created = bookService.createBook(testBook);

    //     // then
    //     assertThat(created.getTitle()).isEqualTo(testBook.getTitle());
    //     verify(bookRepository, times(1)).save(any(Book.class));
    // }

    // @Test
    // void whenUpdateBook_thenReturnUpdatedBook() {
    //     // given
    //     Book updatedBook = new Book();
    //     updatedBook.setTitle("Updated Title");
    //     when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
    //     when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

    //     // when
    //     Optional<Book> updated = bookService.updateBook(1L, updatedBook);

    //     // then
    //     assertThat(updated).isPresent();
    //     assertThat(updated.get().getTitle()).isEqualTo("Updated Title");
    //     verify(bookRepository, times(1)).findById(1L);
    //     verify(bookRepository, times(1)).save(any(Book.class));
    // }

    @Test
    void whenDeleteBook_thenReturnTrue() {
        // given
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // when
        boolean result = bookService.deleteBook(1L);

        // then
        assertThat(result).isTrue();
        verify(bookRepository, times(1)).deleteById(1L);
    }
}