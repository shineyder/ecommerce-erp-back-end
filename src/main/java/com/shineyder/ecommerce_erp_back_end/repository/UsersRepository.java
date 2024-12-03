package com.shineyder.ecommerce_erp_back_end.repository;

import com.shineyder.ecommerce_erp_back_end.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, Integer> {
    Users findByUsername(String username);
}
