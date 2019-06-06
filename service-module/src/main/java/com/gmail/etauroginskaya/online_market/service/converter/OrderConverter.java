package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Order;
import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import com.gmail.etauroginskaya.online_market.service.model.OrderWithUserInfoDTO;

public interface OrderConverter {

    OrderDTO toDTO(Order order);

    OrderWithUserInfoDTO toDTOWithUserInfo(Order order);
}
