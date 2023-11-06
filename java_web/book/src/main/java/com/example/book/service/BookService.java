package com.example.book.service;

import com.example.book.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getBookList() throws Exception;

    Book getBook(Integer id) throws Exception;;
}
