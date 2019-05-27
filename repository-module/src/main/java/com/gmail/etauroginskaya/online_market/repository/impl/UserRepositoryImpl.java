package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User AS U WHERE U.email=:email";
        Query query = entityManager.createQuery(hql)
                .setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getUsersByEmailAsc(int page, int maxResult) {
        String hql = "FROM User AS U ORDER BY U.email ASC";
        Query query = entityManager.createQuery(hql)
                .setFirstResult(page)
                .setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public int deleteUsersById(List<Long> listID) {
        String hql = "UPDATE User U SET U.isDeleted=true WHERE U.id IN :listID";
        Query query = entityManager.createQuery(hql)
                .setParameter("listID", listID);
        return query.executeUpdate();
    }

    @Override
    public int updateUserRoleById(Long userID, Long newRoleID) {
        String hql = "UPDATE User U SET U.role.id=:roleId WHERE U.id=:id";
        Query query = entityManager.createQuery(hql)
                .setParameter("id", userID)
                .setParameter("roleId", newRoleID);
        return query.executeUpdate();
    }

    @Override
    public String getUserPasswordById(Long id) {
        String hql = "SELECT U.password FROM User AS U WHERE U.id=:id";
        Query query = entityManager.createQuery(hql)
                .setParameter("id", id);
        return (String) query.getSingleResult();
    }

    @Override
    public int updateUserPasswordById(Long id, String newPassword) {
        String hql = "UPDATE User U SET U.password=:password WHERE U.id=:id";
        Query query = entityManager.createQuery(hql)
                .setParameter("id", id)
                .setParameter("password", newPassword);
        return query.executeUpdate();
    }
}
