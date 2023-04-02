
package com.brij.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class BookDTO {
    @NotNull(message = "Author can not be null")
    private String author;
    @NotNull
    private Genres genres;
    @NotNull
    private String price;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String publisher;
    @NotNull
    private Language language;

}
