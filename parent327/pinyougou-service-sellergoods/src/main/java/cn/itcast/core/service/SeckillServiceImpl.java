package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    SeckillGoodsDao seckillGoodsDao;

    @Override
    public void updateStatus(Long[] ids, String status) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStatus(status);
        seckillGoods.setCheckTime(new Date());
        for (Long id : ids) {
            seckillGoods.setId(id);
            seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
        }
    }

    @Override
    public PageResult search(Integer page, Integer rows, SeckillGoods seckillGoods) {
        PageHelper.startPage(page,rows);
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        if (null != seckillGoods.getStatus() && !"".equals(seckillGoods.getStatus())){
            criteria.andStatusEqualTo(seckillGoods.getStatus());
        }
        Page<SeckillGoods> p = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(seckillGoodsQuery);
        return new PageResult(p.getTotal(),p.getResult());
    }
}
