package com.example.book.dao;

import com.example.book.model.CartItem;
import com.example.book.model.User;

import java.util.List;

public interface CartDao {
    //新增购物车项
    void addCartItem(CartItem cartItem) throws Exception;
    //修改特定的购物车项
    void updateCartItem(CartItem cartItem) throws Exception;
    //获取特定用户的所有购物车项
    List<CartItem> getCartItemList(User user) throws Exception;
    //删除特定的购物车项
    void delCartItem(CartItem cartItem) throws Exception;
}
