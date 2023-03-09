package com.myspringtutorial.spring6webapptutorial.controllers;

import com.myspringtutorial.spring6webapptutorial.services.AuthorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorController {

    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthors(Model model){

        model.addAttribute("authors",authorService.findAll());

        return "authors";
    }

}
