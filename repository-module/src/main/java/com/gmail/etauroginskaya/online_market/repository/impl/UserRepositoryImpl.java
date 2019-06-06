package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User AS U WHERE (U.email=:email AND U.isDeleted=false)";
        Query query = entityManager.createQuery(hql)
                .setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getUsersByEmailAsc(int page, int maxResult) {
        String hql = "FROM User AS U WHERE U.isDeleted=false ORDER BY U.email ASC";
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
    public void deleteProfilesById(List<Long> listID) {
        String hql = "UPDATE Profile P SET P.isDeleted=true WHERE P.id IN :listID";
        Query query = entityManager.createQuery(hql)
                .setParameter("listID", listID);
        query.executeUpdate();
    }

    @Override
    public int updateUserRoleById(Long userID, Long newRoleID) {
        String hql = "UPDATE User U SET U.role.id=:roleId WHERE (U.id=:id AND U.isDeleted=false)";
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
    public boolean existsByEmail(String email) {
        String hql = "FROM User AS U WHERE (U.email=:email AND U.isDeleted=false)";
        Query query = entityManager.createQuery(hql)
                .setParameter("email", email);
        return !query.getResultList().isEmpty();
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
