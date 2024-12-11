package com.shineyder.ecommerce_erp_back_end.repository;

import com.shineyder.ecommerce_erp_back_end.model.JwtBlacklist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JwtBlacklistRepository extends MongoRepository<JwtBlacklist,String> {
    JwtBlacklist findByTokenEquals(String token);
}
