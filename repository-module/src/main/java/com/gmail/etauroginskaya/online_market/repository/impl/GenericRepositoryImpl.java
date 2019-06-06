package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericRepositoryImpl() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[1];
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public T getById(I id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> getLimitEntityWithOffset(int offset, int limit) {
        String sql = "FROM " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(sql)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<T> getAll() {
        String sql = "FROM " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }

    @Override
    public int getCountOfEntities() {
        String sql = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(sql);
        return ((Number) query.getSingleResult()).intValue();
    }
}
