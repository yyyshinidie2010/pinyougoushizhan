package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * 商家管理
 */
@Service
@Transactional
public class SellerServiceImpl implements  SellerService {

    @Autowired
    private SellerDao sellerDao;
    @Override
    public void add(Seller seller) {
        //密码加密  Spring Security
        seller.setPassword(new BCryptPasswordEncoder().encode(seller.getPassword()));
        seller.setStatus("0");
        sellerDao.insertSelective(seller);

    }

    @Override
    public Seller findOne(String sellerId) {
        return sellerDao.selectByPrimaryKey(sellerId);
    }

    @Override
    public List<Seller> findAll() {
        return sellerDao.selectByExample(null);
    }


}
