package com.example.book.controller;

import com.example.book.model.Book;
import com.example.book.model.Cart;
import com.example.book.model.CartItem;
import com.example.book.model.User;
import com.example.book.service.CartService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;

public class CartController {
    private CartService cartService;

    //加载当前用户的购物车信息
    public String index(HttpServletRequest request) throws Exception {
        User user =(User)request.getSession().getAttribute("currUser");
        Cart cart = cartService.getCart(user);
        user.setCart(cart);
        request.getSession().setAttribute("currUser",user);
        return "cart/cart";
    }

    public String addCart(String bookId,HttpServletRequest request) throws Exception {
        User user = (User)request.getSession().getAttribute("currUser");
        CartItem cartItem = new CartItem(new Book(Integer.valueOf(bookId)),1,user);
        //将指定的图书添加到当前用户的购物车中
        cartService.addOrUpdateCartItem(cartItem,user.getCart());

        return "redirect:cart.do";
    }
    public String editCart(Integer cartItemId , Integer buyCount) throws Exception {
        cartService.updateCartItem(new CartItem(cartItemId , buyCount));
        return "";
    }

    public String cartInfo(HttpServletRequest request) throws Exception {
        User user =(User)request.getSession().getAttribute("currUser");
        Cart cart = cartService.getCart(user);

        //调用Cart中的三个属性的get方法，目的是在此处计算这三个属性的值，否则这三个属性为null，
        //导致的结果就是下一步的gson转化时，为null的属性会被忽略
        cart.getTotalBookCount();
        cart.getTotalCount();
        cart.getTotalMoney();

        Gson gson = new Gson();
        String cartJsonStr = gson.toJson(cart);
        return "json:"+cartJsonStr ;
    }
}
