package com.example.book.dao.impl;

import com.example.book.dao.BookDao;
import com.example.book.model.Book;
import myssm.basedao.BaseDAO;

import java.util.List;

public class BookDaoImpl extends BaseDAO<Book> implements BookDao {
    @Override
    public List<Book> queryBookList() throws Exception {
        return this.executeQuery("select id,bookImg,bookName,price,author,saleCount,bookCount from t_book");
    }

    @Override
    public Book getBook(Integer id) throws Exception {
        List<Book> books = this.executeQuery("select id,bookImg,bookName,price,author,saleCount,bookCount from t_book where id=?",id);
        if(books!=null&&books.size()>0){
            return books.get(0);
        }

        return null;
    }
}
