package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.RoleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
