package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {

    List<Item> getItemsByNameAsc(int page, int maxResult);
}
