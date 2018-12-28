package cn.itcast.core.service;

import cn.itcast.core.dao.ad.ContentDao;
import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.ad.ContentQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Override
    public List<Content> findAll() {
        List<Content> list = contentDao.selectByExample(null);
        return list;
    }

    @Override
    public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page = (Page<Content>) contentDao.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Content content) {
        contentDao.insertSelective(content);
    }

    //修改
    //基于一个数据库基础上的,只操作Mysql数据库  事务可以保证
    //不仅操作Mysql 又操作缓存  将来操作索引库  MQ库

    //Spring可以控制事务 Mysql Redis 正确(太片面)  Spring不支持事务 正确吗?完全错误

    //之前 进入Aop 切面是一个对象 DataSourceTransactionManager dataSource连接Mysql数据库 Mysql数据支持事务 begin transation
    @Override
    public void edit(Content content) {
        //轮播图 少一个    缓存中清除 轮播图
        //今日推荐 多一个  缓存中清除 今日推荐
        //商品精选  Map

        //1:先根据ID查询一个原来的广告 (分类ID 外键)
        Content c = contentDao.selectByPrimaryKey(content.getId());
        //5:修改Mysql数据库
        contentDao.updateByPrimaryKeySelective(content);

        //2:判断原来的广告的分类ID 与现在的广告分类ID是否相同
        if (!content.getCategoryId().equals(c.getCategoryId())) {
            //3:不相同 清除原来的广告分类ID
            redisTemplate.boundHashOps("content").delete(c.getCategoryId());
        }
        //4:清除现在的广告的分类ID
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());

        //之后 进入Aop 切面是一个对象 DataSourceTransactionManager dataSource连接Mysql数据库 rollback

    }
    //之后 进入Aop 切面是一个对象 DataSourceTransactionManager dataSource连接Mysql数据库 commit

    @Override
    public Content findOne(Long id) {
        Content content = contentDao.selectByPrimaryKey(id);
        return content;
    }

    @Override
    public void delAll(Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                contentDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;//五大类型:

    //根据广告分类的ID 查询 广告结果集
    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        //1:先查缓存
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        if (null == contentList || contentList.size() == 0) {
            //3:没有 查询Mysql数据库
            ContentQuery contentQuery = new ContentQuery();
            contentQuery.createCriteria().andCategoryIdEqualTo(categoryId).andStatusEqualTo("1");
            contentQuery.setOrderByClause("sort_order desc");
            contentList = contentDao.selectByExample(contentQuery);
            //保存一份到缓存中 (时间)
            redisTemplate.boundHashOps("content").put(categoryId, contentList);
            redisTemplate.boundHashOps("content").expire(24, TimeUnit.HOURS);

        }
        //4:直接返回
        return contentList;

    }

}
