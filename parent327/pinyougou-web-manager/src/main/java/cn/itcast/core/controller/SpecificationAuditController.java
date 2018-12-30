package cn.itcast.core.controller;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理
 */
@RestController
@RequestMapping("/specificationAudit")
public class SpecificationAuditController {

    @Reference
    private SpecificationService specificationService;

//    //商品分页查询  带条件
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody Specification specification) {
        return specificationService.search(page, rows, specification);
    }
//

    //开始审核
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            specificationService.updateStatus(ids, status);
            return new Result(true, "审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "审核失败");
        }
    }
}
