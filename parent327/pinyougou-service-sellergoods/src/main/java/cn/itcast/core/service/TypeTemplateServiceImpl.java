package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 模板管理
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate) {
        //1:从Mysql查询所有模板对象结果集
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        //2:将上面的结果集保存缓存(Hash类型)
        for (TypeTemplate t : typeTemplates) {
           /* hash
            K:模板ID V:品牌列表
                    hash
            K:模板ID V:规格列表*/
             //[{"id":52,"text":"宝马"},{"id":53,"text":"法拉利"},{"id":54,"text":"蓝博基尼"}]
            redisTemplate.boundHashOps("brandList").put(t.getId(),JSON.parseArray(t.getBrandIds(), Map.class));
            //[{"id":45,"text":"汽车颜色"},{"id":46,"text":"汽车排量"}]
            redisTemplate.boundHashOps("specList").put(t.getId(),findBySpecList(t.getId()));

        }



        //分页插件
        PageHelper.startPage(page, rows);
        PageHelper.orderBy("id desc");
        Page<TypeTemplate> p = (Page<TypeTemplate>) typeTemplateDao.selectByExample(null);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void add(TypeTemplate tt) {
        typeTemplateDao.insertSelective(tt);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(TypeTemplate tt) {
        typeTemplateDao.updateByPrimaryKeySelective(tt);
    }

    @Override
    public List<Map> findBySpecList(Long id) {

        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        //[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = typeTemplate.getSpecIds();
        List<Map> listMap = JSON.parseArray(specIds, Map.class);
        for (Map map : listMap) {

        //map1
           // id: 27
            //text:网络
            //options:list
            SpecificationOptionQuery query = new SpecificationOptionQuery();
            query.createCriteria().andSpecIdEqualTo((long)(Integer)map.get("id"));//Object - 简单类型Integer String-- Long
            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);
            map.put("options",specificationOptions);
        }

        return listMap;
    }
}
