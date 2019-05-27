package com.gmail.etauroginskaya.online_market.repository;

import java.util.List;

public interface GenericRepository<I, T> {

    void persist(T entity);

    void merge(T entity);

    void remove(T entity);

    T getById(I id);

    List<T> getLimitEntityWithOffset(int offset, int limit);

    List<T> getAll();

    int getCountOfEntities();
}
