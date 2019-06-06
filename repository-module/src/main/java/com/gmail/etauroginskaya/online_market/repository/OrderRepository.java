package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {

    List<Order> getOrdersByCreatedDesc(int page, int maxResult);

    List<Order> getOrdersByUserIdCreatedDesc(Long id, int page, int maxResult);

    boolean exists(long number);
}