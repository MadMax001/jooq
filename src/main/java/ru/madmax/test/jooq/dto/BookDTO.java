package ru.madmax.test.jooq.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookDTO {
    private final long id;
    private final String title;
    private final int year;
    private final PublisherDTO publisher;
}
