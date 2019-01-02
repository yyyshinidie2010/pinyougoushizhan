package cn.itcast.core.controller;


import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.OrderQueryService;
import cn.itcast.core.service.SeckillQueryService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderQuery")
public class OrderQueryController {

    @Reference
    private OrderQueryService orderQueryService;

    //查询分页对象
    @RequestMapping("/search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody Order order) {
        PageResult pageResult = orderQueryService.search(pageNum, pageSize, order);
        return pageResult;
    }

    //查询一个品牌
    @RequestMapping("/findOne")
    public Order findOne(Long id) {
        return orderQueryService.findOne(id);
    }
}
