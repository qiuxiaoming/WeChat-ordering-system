package com.xjtu.repository;

import com.xjtu.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 10270 on 2020/7/25.
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    public SellerInfo findByOpenid(String openid);
}
