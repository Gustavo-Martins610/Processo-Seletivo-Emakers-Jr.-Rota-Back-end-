package com.emakers.api_biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.api_biblioteca.repositories.LivroRepository;

@RestController
public class LivroController {

@Autowired
LivroRepository LivroRepository;

}
