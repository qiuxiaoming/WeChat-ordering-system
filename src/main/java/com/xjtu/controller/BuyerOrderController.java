package com.xjtu.controller;

import com.xjtu.VO.ResultVO;
import com.xjtu.converter.OrderForm2OrderDTOConverter;
import com.xjtu.dataobject.OrderMaster;
import com.xjtu.dto.OrderDTO;
import com.xjtu.enums.ResultEnum;
import com.xjtu.exception.SellException;
import com.xjtu.form.OrderForm;
import com.xjtu.repository.OrderMasterRepository;
import com.xjtu.service.BuyerService;
import com.xjtu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.ResultVOUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10270 on 2020/7/21.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确， orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.createOrder(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtils.success(map);
    }


    //订单列表
    @PostMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value="page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage= orderService.findList(openid,pageRequest);

        return ResultVOUtils.success(orderDTOPage.getContent());

    }

    //查询订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO 不安全
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return  ResultVOUtils.success(orderDTO);
    }

    //取消订单
    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtils.success();
    }
}
