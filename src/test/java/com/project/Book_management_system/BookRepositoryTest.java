package com.project.Book_management_system;

import com.project.Book_management_system.model.Book;
import com.project.Book_management_system.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenFindById_thenReturnBook() {
        // given
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(2023);
        
        entityManager.persist(book);
        entityManager.flush();

        // when
        Book found = bookRepository.findById(book.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo(book.getTitle());
        assertThat(found.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    void whenFindByNonExistentId_thenReturnNull() {
        // when
        var found = bookRepository.findById(999L);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    void whenSave_thenBookIsPersisted() {
        // given
        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("New Author");
        book.setIsbn("0987654321");
        book.setPublicationYear(2024);

        // when
        Book saved = bookRepository.save(book);

        // then
        Book found = entityManager.find(Book.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo(book.getTitle());
    }
}