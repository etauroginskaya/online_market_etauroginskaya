package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.ItemRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> getItemsByNameAsc(int page, int maxResult) {
        String hql = "FROM Item AS I WHERE I.isDeleted=false ORDER BY I.name ASC";
        Query query = entityManager.createQuery(hql)
                .setFirstResult(page)
                .setMaxResults(maxResult);
        return query.getResultList();
    }
}
