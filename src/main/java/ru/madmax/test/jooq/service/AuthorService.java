package ru.madmax.test.jooq.service;

import ru.madmax.test.jooq.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO getById(long id);
    List<AuthorDTO> findAll();
}
