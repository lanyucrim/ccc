package com.lanyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.entity.ShoppingCart;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public R<ShoppingCart> addShopCart(ShoppingCart shoppingCart);
    public R<List<ShoppingCart>> getList();
    public R<String> cleanShopCart();
}
