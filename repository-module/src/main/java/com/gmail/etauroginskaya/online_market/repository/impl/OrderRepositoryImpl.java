package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.OrderRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Order> getOrdersByCreatedDesc(int page, int maxResult) {
        String hql = "FROM Order AS O ORDER BY O.created DESC";
        Query query = entityManager.createQuery(hql)
                .setFirstResult(page)
                .setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Order> getOrdersByUserIdCreatedDesc(Long id, int page, int maxResult) {
        String hql = "FROM Order AS O WHERE O.user.id=:id ORDER BY O.created DESC";
        Query query = entityManager.createQuery(hql)
                .setParameter("id", id)
                .setFirstResult(page)
                .setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public boolean exists(long number) {
        String hql = "FROM Order AS O WHERE O.uniqueNumber=:number";
        Query query = entityManager.createQuery(hql)
                .setParameter("number", number);
        return !query.getResultList().isEmpty();
    }
}
