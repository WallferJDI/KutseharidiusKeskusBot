package com.example.chumbatelegramm.dao;

import com.example.chumbatelegramm.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByTelegramMemberId(Long telegramMemberId);
}
