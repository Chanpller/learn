package com.example.book.dao;

import com.example.book.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> queryBookList() throws Exception;

    Book getBook(Integer id) throws Exception;
}
