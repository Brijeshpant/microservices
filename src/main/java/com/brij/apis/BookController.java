package com.brij.apis;

import com.brij.dto.BookDTO;
import com.brij.services.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books")
@Slf4j
public class BookController {
    @Autowired
    BookService bookService;


    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public BookDTO createBook(@Valid @RequestBody BookDTO bookDTO) {

        BookDTO book = bookService.createBook(bookDTO);
        log.info("Book info {}", book);
        return book;
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDTO getBookById(@NotBlank @RequestParam("id") Long id) {
        try {
            return bookService.getBook(id);
        } catch (Exception ex) {
            throw ex;
        }
    }

//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<BookDTO> getBooks() {
//        return bookService.getBooks();
//
//    }
}