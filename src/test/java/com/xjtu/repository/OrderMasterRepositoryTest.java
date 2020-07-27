package com.xjtu.repository;

import com.xjtu.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    private final String OPENID = "321";

    @Autowired
     private OrderMasterRepository repository;
    @Test
    public void testSave(){
        //for (int i = 0; i<20; i++){
            OrderMaster orderMaster = new OrderMaster();
            Integer orderId = 15625892 ;
            orderMaster.setOrderId(orderId.toString());
            orderMaster.setBuyerName("小红");
            orderMaster.setBuyerPhone("156212485165");
            orderMaster.setBuyerAddress("北京");
            orderMaster.setBuyerOpenid(OPENID);
            orderMaster.setOrderAmount(new BigDecimal(2.5));
            OrderMaster  result = repository.save(orderMaster);
            assertNotNull(result);
        //}

    }

    @Test
    public void findByBuyerOpenid() throws Exception {

        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,pageRequest);
        assertNotEquals(0,result.getTotalElements());

    }

}