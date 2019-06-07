package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.OrderService;
import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ORDERS_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ORDER_URL;

@RestController
public class OrderAPIController {

    private final OrderService orderService;

    public OrderAPIController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(API_ORDERS_URL)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(API_ORDER_URL)
    public ResponseEntity getOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        if (orderDTO == null) {
            return new ResponseEntity("Not found order or order is deleted", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity(orderDTO, HttpStatus.OK);
    }
}
