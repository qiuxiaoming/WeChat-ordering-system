package com.xjtu.repository;

import com.xjtu.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void testSave(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123");
        orderDetail.setOrderId("456545");
        orderDetail.setProductId("564565");
        orderDetail.setProductName("小鱼");
        orderDetail.setProductPrice(new BigDecimal(5.2));
        orderDetail.setProductQuantity(454);

        OrderDetail result = repository.save(orderDetail);
        assertNotNull(result);

    }

    @Test
    public void findByOrderId() throws Exception {

        List<OrderDetail> resultList = repository.findByOrderId("456545");
        assertNotEquals(0,resultList.size());
    }

}