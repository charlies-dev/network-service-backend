package com.infy.content.service;

import com.infy.content.entity.Hashtag;
import com.infy.content.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HashtagServiceImpl implements HashtagService {

    @Autowired
    private HashtagRepository repository;

    @Override
    public Hashtag create(Hashtag entity) {
        return null;
    }

    @Override
    public Hashtag getById(Long id) {
        return null;
    }

    @Override
    public List<Hashtag> getAll() {
        return null;
    }

    @Override
    public Hashtag update(Long id, Hashtag entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
