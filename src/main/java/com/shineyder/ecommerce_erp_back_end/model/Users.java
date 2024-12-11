package com.shineyder.ecommerce_erp_back_end.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
@Getter
public class Users {
    @Id
    private final String id;

    @Field("name")
    @Setter
    private String name;

    @Field("email")
    @Setter
    @Indexed(unique = true)
    private String email;

    @Field("password")
    @Setter
    private String password;

    public Users(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
            '}';
    }
}
