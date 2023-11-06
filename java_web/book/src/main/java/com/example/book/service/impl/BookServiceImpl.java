package com.example.book.service.impl;

import com.example.book.dao.BookDao;
import com.example.book.model.Book;
import com.example.book.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    @Override
    public List<Book> getBookList() throws Exception {
        return bookDao.queryBookList();
    }

    @Override
    public Book getBook(Integer id) throws Exception {
        return bookDao.getBook(id);
    }
}
