package com.spring.projects.MVCRest.repositories;

import com.spring.projects.MVCRest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
