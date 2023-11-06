package com.example.book.dao.impl;

import com.example.book.dao.CartDao;
import com.example.book.model.CartItem;
import com.example.book.model.User;
import myssm.basedao.BaseDAO;

import java.util.List;

public class CartDaoImpl extends BaseDAO<CartItem> implements CartDao {
    @Override
    public void addCartItem(CartItem cartItem) throws Exception {
        executeUpdate("insert into t_cart_item values(0,?,?,?)",cartItem.getBook().getId(),cartItem.getBuyCount(),cartItem.getUserBean().getId());
    }

    @Override
    public void updateCartItem(CartItem cartItem) throws Exception {
        executeUpdate("update t_cart_item set buyCount = ? where id = ? " , cartItem.getBuyCount(),cartItem.getId()) ;
    }

    @Override
    public List<CartItem> getCartItemList(User user) throws Exception {
        return executeQuery("select * from t_cart_item where userBean = ? " , user.getId());
    }

    @Override
    public void delCartItem(CartItem cartItem) throws Exception {
        super.executeUpdate("delete from t_cart_item where id = ?" , cartItem.getId());
    }
}
