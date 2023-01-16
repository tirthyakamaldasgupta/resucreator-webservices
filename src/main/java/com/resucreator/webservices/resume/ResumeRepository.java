package com.resucreator.webservices.resume;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResumeRepository extends MongoRepository<Resume, String> {

    List<Resume> findAllByUserId(String userId);
}
