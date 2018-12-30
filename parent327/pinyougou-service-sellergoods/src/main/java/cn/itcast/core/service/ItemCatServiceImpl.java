package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分类管理
 */
@Service
@Transactional
public class ItemCatServiceImpl implements  ItemCatService {
    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        //1:查询Mysql数据中分类结果集 (所有)
        List<ItemCat> itemCats = findAll();
        //2:将所有商品分类结果集保存到缓存中(Hash类型)
        for (ItemCat itemCat : itemCats) {
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
        }
        ItemCatQuery query = new ItemCatQuery();
        query.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(query);
    }

    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    @Override
    public PageResult search(Integer page, Integer rows, ItemCat itemCat) {
        //分页插件
        PageHelper.startPage(page, rows);
        PageHelper.orderBy("id desc");
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = itemCatQuery.createCriteria();
        if (null != itemCat.getAuditStatus() && !"".equals(itemCat.getAuditStatus())) {
            criteria.andAuditStatusEqualTo(itemCat.getAuditStatus());
        }
        Page<ItemCat> p = (Page<ItemCat>) itemCatDao.selectByExample(itemCatQuery);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        ItemCat itemCat = new ItemCat();
        itemCat.setAuditStatus(status);
        for (Long id : ids) {
            itemCat.setId(id);
            itemCatDao.updateByPrimaryKeySelective(itemCat);
        }
    }
}
