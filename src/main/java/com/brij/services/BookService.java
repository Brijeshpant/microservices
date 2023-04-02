package com.brij.services;

import com.brij.domain.Book;
import com.brij.dto.BookDTO;
import com.brij.exceptions.MissingRecordException;
import com.brij.mappers.BookMapper;
import com.brij.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    BookRepository repository;

    @Autowired
    BookMapper bookMapper;

    public List<BookDTO> getBooks() {
        List<Book> all = repository.findAll();
        return all.stream().map(bookMapper::buildBookDTO).collect(Collectors.toList());
    }

    public BookDTO getBook(Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isEmpty()) {
            throw new MissingRecordException(String.format("Book not found with Id %s", id));
        }
        return bookMapper.buildBookDTO(book.get());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book save = repository.save(bookMapper.buildBook(bookDTO));
        return bookMapper.buildBookDTO(save);
    }
}
