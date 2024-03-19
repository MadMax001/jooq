-- liquibase formatted sql

-- changeset id:initial_db
-- preconditions onFail:HALT
CREATE SCHEMA IF NOT EXISTS pet;

CREATE TABLE IF NOT EXISTS pet.author (
   id BIGSERIAL NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pet.publisher (
   id BIGSERIAL NOT NULL,
   title VARCHAR(255),
   CONSTRAINT pk_publisher PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pet.book (
   id BIGSERIAL NOT NULL,
   title VARCHAR(255),
   "year" int NOT NULL,
   CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pet.publisher_book (
    publisher_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (publisher_id, book_id),
    CONSTRAINT fk_pb_publisher FOREIGN KEY (publisher_id)  REFERENCES pet.publisher (id),
    CONSTRAINT fk_pb_book FOREIGN KEY (book_id)  REFERENCES pet.book (id)
)
