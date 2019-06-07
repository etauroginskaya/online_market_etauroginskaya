package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Role;

<<<<<<< HEAD
import java.sql.Connection;
import java.util.List;

public interface RoleRepository extends ConnectionRepository {

    List<Role> getRoles(Connection connection);
=======
public interface RoleRepository extends GenericRepository<Long, Role> {
>>>>>>> develop
}
