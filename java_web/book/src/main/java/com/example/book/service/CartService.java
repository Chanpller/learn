package com.example.book.service;

import com.example.book.model.Cart;
import com.example.book.model.CartItem;
import com.example.book.model.User;

import java.util.List;

public interface CartService {
    void addCartItem(CartItem cartItem) throws Exception;
    void updateCartItem(CartItem cartItem) throws Exception;
    void addOrUpdateCartItem(CartItem cartItem , Cart cart) throws Exception;

    //获取指定用户的所有购物车项列表（需要注意的是，这个方法内部查询的时候，会将book的详细信息设置进去）
    List<CartItem> getCartItemList(User user) throws Exception;

    //加载特定用户的购物车信息
    Cart getCart(User user) throws Exception;
}
