package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum,pageSize);
//        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
//        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();
//        if (null != seckillOrder.getSellerId() && !"".equals(seckillOrder.getSellerId().trim())){
//            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
//        }
//        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
//        return new PageResult(p.getTotal(),p.getResult());
        OrderQuery orderQuery = new OrderQuery();
        OrderQuery.Criteria criteria = orderQuery.createCriteria();
        if (null != order.getSellerId() && !"".equals(order.getSellerId().trim())){
            criteria.andSellerIdEqualTo(order.getSellerId());
        }
        Page<Order> p = (Page<Order>) orderDao.selectByExample(orderQuery);
        return new PageResult(p.getTotal(),p.getResult());
    }

    @Override
    public Order findOne(Long id) {
        return orderDao.selectByPrimaryKey(id);
    }
}
