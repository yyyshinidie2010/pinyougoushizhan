package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

public interface SeckillQueryService {
    PageResult search(Integer pageNum, Integer pageSize, SeckillOrder seckillOrder);

    SeckillOrder findOne(Long id);
}
