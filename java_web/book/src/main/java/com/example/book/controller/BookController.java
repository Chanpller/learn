package com.example.book.controller;

import com.example.book.model.Book;
import com.example.book.service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BookController {

    private BookService bookService ;

    public String index(HttpServletRequest httpServletRequest) throws Exception {
        List<Book> bookList = bookService.getBookList();
        httpServletRequest.getSession().setAttribute("bookList",bookList);
        return "index";
    }
}
