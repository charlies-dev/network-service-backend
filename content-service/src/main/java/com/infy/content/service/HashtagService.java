package com.infy.content.service;

import com.infy.content.entity.Hashtag;
import java.util.List;

public interface HashtagService {

    Hashtag create(Hashtag entity);

    Hashtag getById(Long id);

    List<Hashtag> getAll();

    Hashtag update(Long id, Hashtag entity);

    void delete(Long id);
}
