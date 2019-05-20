package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.GenericRepository;
import com.gmail.etauroginskaya.online_market.repository.exception.DatabaseConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {

    private static Logger logger = LoggerFactory.getLogger(GenericRepositoryImpl.class);

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    public GenericRepositoryImpl() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperClass.getActualTypeArguments()[1];
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseConnectionException("Connection Failed! Check output console.", e);
        }
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
    public int getCountOfEntities() {
        String sql = "SELECT COUNT(*) FROM " + entityClass.getName() + " c";
        Query query = entityManager.createQuery(sql);
        return ((Number) query.getSingleResult()).intValue();
    }
}
