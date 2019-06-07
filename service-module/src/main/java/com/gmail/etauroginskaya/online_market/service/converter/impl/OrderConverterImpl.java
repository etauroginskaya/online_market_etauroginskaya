package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Order;
import com.gmail.etauroginskaya.online_market.service.model.OrderStatusEnum;
import com.gmail.etauroginskaya.online_market.service.converter.OrderConverter;
import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import com.gmail.etauroginskaya.online_market.service.model.OrderWithUserInfoDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderConverterImpl implements OrderConverter {

    @Override
    public OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setNumber(order.getUniqueNumber());
        orderDTO.setItemName(order.getItem().getName());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setStatus(OrderStatusEnum.valueOf(order.getStatus()));
        orderDTO.setTotalPrice(order.getItem().getPrice().multiply(new BigDecimal(order.getQuantity())));
        return orderDTO;
    }

    @Override
    public OrderWithUserInfoDTO toDTOWithUserInfo(Order order) {
        OrderWithUserInfoDTO orderDTO = new OrderWithUserInfoDTO();
        orderDTO.setNumber(order.getUniqueNumber());
        orderDTO.setItemName(order.getItem().getName());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setStatus(OrderStatusEnum.valueOf(order.getStatus()));
        orderDTO.setTotalPrice(order.getItem().getPrice().multiply(new BigDecimal(order.getQuantity())));
        orderDTO.setUserName(order.getUser().getName());
        orderDTO.setSurname(order.getUser().getSurname());
        orderDTO.setTelephone(order.getUser().getProfile().getTelephone());
        orderDTO.setEmail(order.getUser().getEmail());
        return orderDTO;
    }
}
