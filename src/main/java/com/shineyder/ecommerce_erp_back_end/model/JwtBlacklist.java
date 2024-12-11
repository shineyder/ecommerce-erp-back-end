package com.shineyder.ecommerce_erp_back_end.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("jwtBlacklist")
@Getter
@Setter
public class JwtBlacklist {
    @Id
    private String id;
    private String token;

    public JwtBlacklist(String id, String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtBlacklist{" +
                "id='" + this.getId() + '\'' +
                ", token='" + this.getToken() + '\'' +
            '}';
    }
}
