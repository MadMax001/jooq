package ru.madmax.test.jooq.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class AuthorDTO {
    private final long id;
    private final String name;
    private final Set<BookDTO> books;
}
