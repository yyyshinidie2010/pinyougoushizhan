package cn.itcast.core.controller;


import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;

    //    //商品分页查询  带条件
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillGoods seckillGoods) {
        return seckillService.search(page, rows, seckillGoods);
    }
//

    //开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            seckillService.updateStatus(ids, status);
            return new Result(true, "审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "审核失败");
        }
    }
}
