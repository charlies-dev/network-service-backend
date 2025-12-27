package com.infy.user.service;

import com.infy.user.entity.Skill;
import com.infy.user.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private  SkillRepository repository;

    @Override
    public Skill create(Skill entity) {
        return null;
    }

    @Override
    public Skill getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return repository.findAll();
    }

    @Override
    public Skill update(Long id, Skill entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
