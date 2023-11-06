package com.example.book.service.impl;

import com.example.book.dao.CartDao;
import com.example.book.model.Book;
import com.example.book.model.Cart;
import com.example.book.model.CartItem;
import com.example.book.model.User;
import com.example.book.service.BookService;
import com.example.book.service.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServiceImpl implements CartService {
    private CartDao cartDao;
    private BookService bookService ;

    @Override
    public void addCartItem(CartItem cartItem) throws Exception {
        cartDao.addCartItem(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) throws Exception {
        cartDao.updateCartItem(cartItem);
    }

    @Override
    public void addOrUpdateCartItem(CartItem cartItem , Cart cart)throws Exception  {
        //1.如果当前用户的购物车中已经存在这个图书了，那么将购物车中这本图书的数量+1
        //2.否则，在我的购物车中新增一个这本图书的CartItem，数量是1
        //判断当前用户的购物车中是否有这本书的CartItem，有->update , 无->add
        if(cart!=null){
            Map<Integer, CartItem> cartItemMap = cart.getCartItemMap();
            if(cartItemMap==null){
                cartItemMap = new HashMap<>();
            }

            if(cartItemMap.containsKey(cartItem.getBook().getId())){
                CartItem cartItemTemp = cartItemMap.get(cartItem.getBook().getId());
                cartItemTemp.setBuyCount(cartItemTemp.getBuyCount()+1);
                updateCartItem(cartItemTemp);
            }else{
                addCartItem(cartItem);
            }
        }else{  //连购物车都没有的情况
            addCartItem(cartItem);
        }
    }

    @Override
    public List<CartItem> getCartItemList(User user)throws Exception  {
        List<CartItem> cartItemList = cartDao.getCartItemList(user);
        for(CartItem cartItem : cartItemList){
            Book book = bookService.getBook(cartItem.getBook().getId());
            cartItem.setBook(book);
            //此处需要调用getXj()，目的是执行getXj()内部的代码，让book的price乘以buyCount，从而计算出xj这个属性的值
            cartItem.getXj();
        }

        return cartItemList;
    }

    @Override
    public Cart getCart(User user) throws Exception {
        List<CartItem> cartItemList = getCartItemList(user);
        Map<Integer,CartItem> cartItemMap = new HashMap<>();
        for (CartItem cartItem : cartItemList){
            cartItemMap.put(cartItem.getBook().getId(),cartItem);
        }
        Cart cart = new Cart();
        cart.setCartItemMap(cartItemMap);
        return cart;
    }
}
