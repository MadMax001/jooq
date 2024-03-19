-- liquibase formatted sql

-- changeset id:addContent
-- preconditions onFail:HALT

insert into pet.author (name) values
('А. С. Пушкин'),
('Н. В. Гоголь'),
('К. Седых'),
('М. Ю. Лермонтов'),
('С. Цвейг');

insert into pet.publisher (title) values
('ЛенИздат'),
('Гос. издательство художественной литературы');

insert into pet.book (title, "year") values
('Избранное', 1960),
('Избранные сочиеннеия в двух томах', 1959),
('Мертвые души', 1961),
('Собрание сочинений', 1959),
('Даурия', 1989);

insert into pet.author_book (author_id, book_id) values
((select id from pet.author where name='А. С. Пушкин'), (select id from pet.book where title='Собрание сочинений')),
((select id from pet.author where name='Н. В. Гоголь'), (select id from pet.book where title='Мертвые души')),
((select id from pet.author where name='К. Седых'), (select id from pet.book where title='Даурия')),
((select id from pet.author where name='М. Ю. Лермонтов'), (select id from pet.book where title='Избранные сочиеннеия в двух томах')),
((select id from pet.author where name='С. Цвейг'), (select id from pet.book where title='Избранное'));

insert into pet.publisher_book values
((select id from pet.publisher where title='Гос. издательство художественной литературы'), (select id from pet.book where title='Собрание сочинений')),
((select id from pet.publisher where title='Гос. издательство художественной литературы'), (select id from pet.book where title='Мертвые души')),
((select id from pet.publisher where title='ЛенИздат'), (select id from pet.book where title='Даурия')),
((select id from pet.publisher where title='Гос. издательство художественной литературы'), (select id from pet.book where title='Избранные сочиеннеия в двух томах')),
((select id from pet.publisher where title='Гос. издательство художественной литературы'), (select id from pet.book where title='Избранное'));

