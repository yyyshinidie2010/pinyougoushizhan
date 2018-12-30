package cn.itcast.core.service;

import cn.itcast.core.pojo.seller.Seller;

import java.util.List;

public interface SellerService {
    void add(Seller seller);

    Seller findOne(String sellerId);

    List<Seller> findAll();
}
