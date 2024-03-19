package ru.madmax.test.jooq.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.madmax.test.jooq.dto.AuthorDTO;
import ru.madmax.test.jooq.dto.BookDTO;
import ru.madmax.test.jooq.dto.PublisherDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.madmax.test.jooq.model.pet.tables.Author.AUTHOR;
import static ru.madmax.test.jooq.model.pet.tables.AuthorBook.AUTHOR_BOOK;
import static ru.madmax.test.jooq.model.pet.tables.Book.BOOK;
import static ru.madmax.test.jooq.model.pet.tables.Publisher.PUBLISHER;
import static ru.madmax.test.jooq.model.pet.tables.PublisherBook.PUBLISHER_BOOK;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private static final String ALIAS_AUTHOR_ID = "AUTHOR_ID";
    private static final String ALIAS_AUTHOR_NAME = "AUTHOR_NAME";
    private static final String ALIAS_BOOK_ID = "BOOK_ID";
    private static final String ALIAS_BOOK_TITLE = "BOOK_TITLE";
    private static final String ALIAS_BOOK_YEAR = "BOOK_YEAR";
    private static final String ALIAS_PUBLISHER = "PUBLISHER";

    private final DSLContext create;
    @Override
    public AuthorDTO getById(long id) {
        var authorRecords = create.select(
                        AUTHOR.ID.as(ALIAS_AUTHOR_ID),
                        AUTHOR.NAME.as(ALIAS_AUTHOR_NAME),
                        BOOK.ID.as(ALIAS_BOOK_ID),
                        BOOK.TITLE.as(ALIAS_BOOK_TITLE),
                        BOOK.YEAR.as(ALIAS_BOOK_YEAR),
                        PUBLISHER.TITLE.as(ALIAS_PUBLISHER)
                )
                .from(
                        AUTHOR
                                .join(AUTHOR_BOOK).on(AUTHOR_BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                                .join(BOOK).on(AUTHOR_BOOK.BOOK_ID.eq(BOOK.ID))
                                .join(PUBLISHER_BOOK).on(PUBLISHER_BOOK.BOOK_ID.eq(BOOK.ID))
                                .join(PUBLISHER).on(PUBLISHER_BOOK.PUBLISHER_ID.eq(PUBLISHER.ID))
                )
                .where(AUTHOR.ID.eq(id))
                .fetch();
        Set<BookDTO> books = authorRecords.stream().map(curRecord ->
                new BookDTO(
                        (long) curRecord.get(ALIAS_BOOK_ID),
                        (String) curRecord.get(ALIAS_BOOK_TITLE),
                        (int) curRecord.get(ALIAS_BOOK_YEAR),
                        new PublisherDTO((String) curRecord.get(ALIAS_PUBLISHER))
                )
        ).collect(Collectors.toSet());

        return authorRecords.stream().findFirst().map(author ->
                    new AuthorDTO(
                            (long) author.get(ALIAS_AUTHOR_ID),
                            (String) author.get(ALIAS_AUTHOR_NAME),
                            books
                    )
                )
                .orElseThrow();
    }

    public List<AuthorDTO> findAll() {
        var authors = create.select(
                        AUTHOR.ID.as(ALIAS_AUTHOR_ID),
                        AUTHOR.NAME.as(ALIAS_AUTHOR_NAME),
                        BOOK.ID.as(ALIAS_BOOK_ID),
                        BOOK.TITLE.as(ALIAS_BOOK_TITLE),
                        BOOK.YEAR.as(ALIAS_BOOK_YEAR),
                        PUBLISHER.TITLE.as(ALIAS_PUBLISHER)
                )
                .from(
                        AUTHOR
                                .join(AUTHOR_BOOK).on(AUTHOR_BOOK.AUTHOR_ID.eq(AUTHOR.ID))
                                .join(BOOK).on(AUTHOR_BOOK.BOOK_ID.eq(BOOK.ID))
                                .join(PUBLISHER_BOOK).on(PUBLISHER_BOOK.BOOK_ID.eq(BOOK.ID))
                                .join(PUBLISHER).on(PUBLISHER_BOOK.PUBLISHER_ID.eq(PUBLISHER.ID))
                )
                .fetch();
        Map<Long, Set<BookDTO>> booksMap = authors.stream().collect(
                Collectors.groupingBy(
                        curRecord -> (long) curRecord.get(ALIAS_AUTHOR_ID),
                        Collectors.mapping(
                                sameRecord -> new BookDTO(
                                        (long) sameRecord.get(ALIAS_BOOK_ID),
                                        (String) sameRecord.get(ALIAS_BOOK_TITLE),
                                        (int) sameRecord.get(ALIAS_BOOK_YEAR),
                                        new PublisherDTO((String) sameRecord.get(ALIAS_PUBLISHER))
                                ),
                                Collectors.toSet()
                        )
        ));


        return authors.stream().map(curRecord -> new AuthorDTO(
                        (long) curRecord.get(ALIAS_AUTHOR_ID),
                        (String) curRecord.get(ALIAS_AUTHOR_NAME),
                        booksMap.getOrDefault(
                                (long) curRecord.get(ALIAS_AUTHOR_ID),
                                Collections.emptySet()
                        )
                )
        ).toList();
    }

}
