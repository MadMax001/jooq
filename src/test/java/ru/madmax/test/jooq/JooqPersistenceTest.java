package ru.madmax.test.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.madmax.test.jooq.model.pet.tables.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AppConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class JooqPersistenceTest {
    private final DSLContext create;

    @Test
    void countAuthors() {
        var count = create.selectCount().from(Book.BOOK).fetchOne(0, Integer.class);
        assertThat(count).isEqualTo(5);
    }
}
