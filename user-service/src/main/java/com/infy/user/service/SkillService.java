package com.infy.user.service;

import com.infy.user.entity.Skill;
import java.util.List;

public interface SkillService {

    Skill create(Skill entity);

    Skill getById(Long id);

    List<Skill> getAll();

    Skill update(Long id, Skill entity);

    void delete(Long id);
}
