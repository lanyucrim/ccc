package com.lanyu.controller;


import com.lanyu.common.R;
import com.lanyu.entity.ShoppingCart;
import com.lanyu.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> getList()
    {
        return shoppingCartService.getList();
    }

    @PostMapping("/add")
    public R<ShoppingCart> addShopCart(@RequestBody ShoppingCart shoppingCart)
    {
        return shoppingCartService.addShopCart(shoppingCart);
    }

    @DeleteMapping("clean")
    public R<String> cleanShopCart()
    {
        return shoppingCartService.cleanShopCart();
    }
}
