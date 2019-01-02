package cn.itcast.core.controller;


import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillQueryService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillQuery")
public class SeckillQueryController {

    @Reference
    private SeckillQueryService seckillQueryService;

    //查询分页对象  带条件   $scope.searchEntity = '{name:O,fr..:O}'
    @RequestMapping("/search")
    public PageResult search(Integer pageNum, Integer pageSize, @RequestBody SeckillOrder seckillOrder){
        PageResult pageResult = seckillQueryService.search(pageNum, pageSize, seckillOrder);
        return pageResult;
    }
    //查询一个品牌
    @RequestMapping("/findOne")
    public SeckillOrder findOne(Long id){
        SeckillOrder one = seckillQueryService.findOne(id);
        return one;

    }
}
