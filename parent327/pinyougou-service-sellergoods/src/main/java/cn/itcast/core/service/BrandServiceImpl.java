package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌管理
 */
@Service
@Transactional
public class BrandServiceImpl implements  BrandService {

    @Autowired
    private BrandDao brandDao;

    //查询所有
    public List<Brand> findAll(){
        return brandDao.selectByExample(null);
    }
}
