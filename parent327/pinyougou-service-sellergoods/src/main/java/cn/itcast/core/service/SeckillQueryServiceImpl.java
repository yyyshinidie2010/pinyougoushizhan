package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeckillQueryServiceImpl implements SeckillQueryService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;


    @Override
    public PageResult search(Integer pageNum, Integer pageSize, SeckillOrder seckillOrder) {
        PageHelper.startPage(pageNum,pageSize);
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();
        if (null != seckillOrder.getSellerId() && !"".equals(seckillOrder.getSellerId().trim())){
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }
        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public SeckillOrder findOne(Long id) {
        SeckillOrder seckillOrder = seckillOrderDao.selectByPrimaryKey(id);
        return seckillOrder;
    }
}