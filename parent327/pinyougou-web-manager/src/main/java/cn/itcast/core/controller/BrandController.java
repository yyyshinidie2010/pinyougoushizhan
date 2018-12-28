package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌管理
 */
@RestController
@RequestMapping("/brand")
public class BrandController {


    @Reference
    private BrandService brandService;


    //查询所有品牌结果集
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }
}
