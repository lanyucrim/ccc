package com.lanyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.BaseContext;
import com.lanyu.common.R;
import com.lanyu.entity.ShoppingCart;
import com.lanyu.mapper.ShoppingCartMapper;
import com.lanyu.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {


    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Override
    public R<ShoppingCart> addShopCart(ShoppingCart shoppingCart) {
        Long currentId= BaseContext.getCurrentId();
        shoppingCart.setUserId((currentId));

        Long dishId=shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,currentId);
        if(dishId!=null)
        {
            lqw.eq(ShoppingCart::getDishId,dishId);
            lqw.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());

        }
        else
        {
            lqw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart getOne= this.getOne(lqw);

        if(getOne!=null)
        {
            Integer number= getOne.getNumber();
            getOne.setNumber(number+1);
            shoppingCartMapper.updateById(getOne);
        }
        else{
            shoppingCart.setNumber(1);
            shoppingCartMapper.insert(shoppingCart);
            getOne=shoppingCart;
        }
        return  R.success(getOne);

    }

    @Override
    public R<List<ShoppingCart>> getList() {
        Long currentId= BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> shoppingCartList=shoppingCartMapper.selectList(lqw);

        return R.success(shoppingCartList);
    }

    @Override
    public R<String> cleanShopCart() {
        Long currentId= BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,currentId);

        shoppingCartMapper.delete(lqw);
        return R.success("成功");
    }
}
