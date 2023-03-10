package com.resucreator.webservices.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User findByUserName(String userName);
}
