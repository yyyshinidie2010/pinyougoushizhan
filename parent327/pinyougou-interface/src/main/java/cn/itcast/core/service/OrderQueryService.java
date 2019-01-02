package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

public interface OrderQueryService {
    PageResult search(Integer pageNum, Integer pageSize, Order order);

    Order findOne(Long id);
}
