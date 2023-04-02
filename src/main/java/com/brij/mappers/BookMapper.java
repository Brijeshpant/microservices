package com.brij.mappers;

import com.brij.domain.Book;
import com.brij.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO buildBookDTO(Book book);
    Book buildBook(BookDTO book);
}
