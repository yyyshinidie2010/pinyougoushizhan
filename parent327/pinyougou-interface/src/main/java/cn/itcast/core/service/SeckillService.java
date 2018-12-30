package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import entity.PageResult;

public interface SeckillService {

    void updateStatus(Long[] ids, String status);

    PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods);
}
