package ru.madmax.test.jooq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.madmax.test.jooq.dto.AuthorDTO;
import ru.madmax.test.jooq.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorDTO getById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @GetMapping("")
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

}
