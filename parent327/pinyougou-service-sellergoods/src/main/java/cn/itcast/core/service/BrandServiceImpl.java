package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        //分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询
        Page<Brand> p = (Page<Brand>) brandDao.selectByExample(null);


        return new PageResult(p.getTotal(), p.getResult());
    }
    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Brand brand) {
        //分页插件
        PageHelper.startPage(pageNum,pageSize);

        BrandQuery brandQuery = new BrandQuery();

        BrandQuery.Criteria criteria = brandQuery.createCriteria();

        //判断品牌名称   "   "
        if(null != brand.getName() && !"".equals(brand.getName().trim())){
            criteria.andNameLike("%"+ brand.getName().trim() + "%");
        }
        //判断品牌首字母
        if(null != brand.getFirstChar() && !"".equals(brand.getFirstChar().trim())){
            criteria.andFirstCharEqualTo(brand.getFirstChar().trim());
        }
        if (null != brand.getAuditStatus() && !"".equals(brand.getAuditStatus())) {
            criteria.andAuditStatusEqualTo(brand.getAuditStatus());
        }
        //查询
        Page<Brand> p = (Page<Brand>) brandDao.selectByExample(brandQuery);

        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        Brand brand = new Brand();
        brand.setAuditStatus(status);
        //品牌表ID
        for (Long id : ids) {
            brand.setId(id);
            //1:品牌状态更改
            brandDao.updateByPrimaryKeySelective(brand);
        }
    }

    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);

             /*   insert into tb_brand (id,name,100) values (null,宝马,B,null,null,...); 100+
                insert into tb_brand (name,firstChar) values (宝马,B)  好*/
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Long[] ids) {
        //勉强可以
    /*    for (Long id : ids) {
            brandDao.deleteByPrimaryKey(id);
        }*/

        //delete from tb_brand where id in (1,2,3)  自己写 逆向工程

        BrandQuery brandQuery = new BrandQuery();
        brandQuery.createCriteria().andIdIn(Arrays.asList(ids));
        brandDao.deleteByExample(brandQuery);


    }


}