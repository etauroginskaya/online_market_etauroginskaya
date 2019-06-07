package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.OrderDTO;
import com.gmail.etauroginskaya.online_market.service.model.OrderWithUserInfoDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Page<OrderDTO> getOrderPageByCreatedDesc(int pageSize, int currentPage);

    OrderWithUserInfoDTO getOrderByNumber(Long number);

    void updateOrderStatus(Long number, String newStatus);

    void addOrder(Long userID, Long itemID, int quantity);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    Page<OrderDTO> getOrderPageCreatedDesc(Long userID, int pageSize, int currentPage);
}
