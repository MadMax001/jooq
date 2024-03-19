-- liquibase formatted sql

-- changeset id:addAuthorBookRelation
-- preconditions onFail:HALT

CREATE TABLE IF NOT EXISTS pet.author_book (
    author_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (author_id, book_id),
    CONSTRAINT fk_ab_author FOREIGN KEY (author_id)  REFERENCES pet.author (id),
    CONSTRAINT fk_ab_book FOREIGN KEY (book_id)  REFERENCES pet.book (id)
)