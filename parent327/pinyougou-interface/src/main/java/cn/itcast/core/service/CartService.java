package cn.itcast.core.service;

import cn.itcast.core.pojo.Cart;
import cn.itcast.core.pojo.item.Item;

import java.util.List;

public interface CartService {
    Item findItemById(Long itemId);

    List<Cart> findCartList(List<Cart> cartList);

    void merge(List<Cart> cartList, String name);

    List<Cart> findCartListFromRedis(String name);
}
